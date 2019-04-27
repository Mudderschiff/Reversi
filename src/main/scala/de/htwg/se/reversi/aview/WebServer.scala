package de.htwg.se.reversi.aview
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging
import akka.actor.{Actor, ActorSystem, Props, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import spray.json.DefaultJsonProtocol._
import scala.concurrent.Future
import scala.concurrent.duration._
import de.htwg.se.reversi.controller.controllerComponent._

import scala.swing.Reactor

class Auction extends Actor with ActorLogging {
  var bids = List.empty[Bid]
  def receive = {
    case bid @ Bid(userId, offer) =>
      bids = bids :+ bid
      log.info(s"Bid complete: $userId, $offer")
    case GetBids => sender() ! Bids(bids)
    case _ => log.info("Invalid message")
  }
}

case class Bid(row: String, col: Int)
case object GetBids
case class Bids(bids: List[Bid])


class WebServer(controller: ControllerInterface) extends Reactor{
    listenTo(controller)
    // these are from spray-json
    implicit val bidFormat = jsonFormat2(Bid)
    implicit val bidsFormat = jsonFormat1(Bids)

      implicit val system = ActorSystem()
      implicit val materializer = ActorMaterializer()
      // needed for the future flatMap/onComplete in the end
      implicit val executionContext = system.dispatcher

      val auction = system.actorOf(Props[Auction], "auction")

      val route =
        path("") {
          put {
            parameter("row".as[Int], "col".as[Int]) { (row, column) =>
              // place a bid, fire-and-forget
              controller.set(row, column, controller.getActivePlayer())
              //auction ! Bid(user, bid)
              complete((StatusCodes.Accepted, "cell set"))
            }
          } ~
            get {
              //implicit val timeout: Timeout = 5.seconds

              // query the actor for the current auction state
              //val bids: Future[Bids] = (auction ? GetBids).mapTo[Bids]
              //complete(bids)
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + controller.gridToString +"</pre>"))
            }
        }

      val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
      println("Server online at http://localhost:8080/")
  /*
      println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  if (StdIn.readLine() == "q") {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }
  */

}