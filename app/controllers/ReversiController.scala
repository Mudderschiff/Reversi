package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.reversi.Reversi
import de.htwg.se.reversi.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.reversi.controller.controllerComponent.{BotStatus, Finished, CellChanged, GameStatus, GridSizeChanged}
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.actor._

import scala.swing.Reactor

@Singleton
class ReversiController @Inject()(cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
  val gameController = Reversi.controller
  def message = GameStatus.message(gameController.gameStatus)

  def about= Action { implicit request =>
    Ok(views.html.index())
  }


  def reversi = Action { implicit request =>
    Ok(views.html.reversi(gameController, message))
  }
  def set(row: Int, col: Int) = Action {implicit request =>
    gameController.set(row,col,gameController.getActivePlayer())
    Ok(views.html.reversi(gameController, message))
  }
  def resize(size: Int) = Action {implicit request =>
    gameController.resize(size)
    Ok(views.html.reversi(gameController, message))
  }

  def newgame() = Action {implicit request =>
    gameController.createNewGrid()
    Ok(views.html.reversi(gameController, message))
  }

  def gridtoJson = Action { implicit request =>
    Ok(gameController.toJson)
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      println("Connect received")
      SudokuWebSocketActorFactory.create(out)
    }
  }

  object SudokuWebSocketActorFactory {
    def create(out: ActorRef) = {
      Props(new SudokuWebSocketActor(out))
    }
  }

  class SudokuWebSocketActor(out: ActorRef) extends Actor with Reactor {
    listenTo(gameController)

    def receive = {
      case msg: String =>
        out ! (gameController.toJson.toString())
        println("Sent Json to Client"+ msg)
    }

    reactions += {
      case event: GridSizeChanged => sendJsonToClient
      case event: CellChanged     => sendJsonToClient
      case event: Finished => sendJsonToClient
      case event: BotStatus => sendJsonToClient
    }

    def sendJsonToClient = {
      println("Received event from Controller")
      out ! (gameController.toJson.toString)
    }
  }


}