package de.htwg.se.reversi.aview
import akka.http.scaladsl.model._
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import de.htwg.se.reversi.controller.controllerComponent._

import scala.swing.Reactor

class Auction(controller: ControllerInterface) extends Actor with ActorLogging with Reactor {
  listenTo(controller)
  def receive = {
    case (row,column) =>
      controller.set(row.asInstanceOf[Int], column.asInstanceOf[Int], controller.getActivePlayer())
      log.info(s"Cell Set:" + row + " " + column)
    case GetGrid => sender() ! controller.gridToString
    case _ => log.info("Invalid message")
  }
}
case object GetGrid

class WebServer(controller: ControllerInterface) {
      implicit val system = ActorSystem()
      implicit val materializer = ActorMaterializer()
      // needed for the future flatMap/onComplete in the end
      //implicit val executionContext = system.dispatcher

      val auction = system.actorOf(Props(new Auction(controller)), "auction")

      val route =
        path("") {
          put {
            parameter("row".as[Int], "col".as[Int]) { (row, column) =>
              auction ! (row, column)
              complete((StatusCodes.Accepted, "cell set"))
            }
          } ~
            get {
              implicit val timeout: Timeout = 5.seconds
              val grid: Future[String] = (auction ? GetGrid).mapTo[String]
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + Await.result(grid, Duration.Inf) +"</pre>"))
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