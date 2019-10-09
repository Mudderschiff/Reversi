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
      implicit val executionContext = system.dispatcher

      val auction = system.actorOf(Props(new Auction(controller)), "auction")

      val route = {
        path("reversi") {
          put {
            parameter("row".as[Int], "col".as[Int]) { (row, column) =>
              auction ! (row, column)
              complete((StatusCodes.Accepted, "cell set\n"))
            }
          }~
            get {
              implicit val timeout: Timeout = 5.seconds
              val grid: Future[String] = (auction ? GetGrid).mapTo[String]
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + Await.result(grid, Duration.Inf) +"</pre>"))
            }
        }~
        path("reversi" / "save") {
          get {
            controller.save()
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + controller.gridToString +"</pre>"))
          }
        }~
        path("reversi" / "load") {
          get {
            controller.load()
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + controller.gridToString +"</pre>"))
          }
        }~
        path("reversi" / "loadDB") {
          get {
            val list = controller.getAllGrids.mkString(", ")
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" +  list +"</pre>"))
          }
        }~
        path("reversi" / "saveDB") {
          get {
            controller.saveGrid(controller.gridToString,controller.getActivePlayer())
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>saved</pre>"))
            complete((StatusCodes.Accepted, "saved\n"))
          }
        }~
        path("reversi" / "deletegrid") {
          put {
            parameter("id".as[Int]) { id =>
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<pre>" + controller.deleteGridById(id)  + "</pre>"))
            }
          }
          }
      }
      val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shut

  }

}