package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.reversi.Reversi
import de.htwg.se.reversi.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.reversi.controller.controllerComponent.{BotStatus, CellChanged, Finished, GameStatus, GridSizeChanged}
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.actor._
import org.webjars.play.WebJarsUtil
import play.api.i18n.I18nSupport
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import scala.swing.Reactor

@Singleton
class ReversiController @Inject()(cc: ControllerComponents) (
  implicit
  webJarsUtil: WebJarsUtil,
  assets: AssetsFinder,
  system: ActorSystem,
  mat: Materializer) extends AbstractController(cc) with I18nSupport {
  val gameController = Reversi.controller
  def message = GameStatus.message(gameController.gameStatus)

  def about= Action { implicit request =>
    Ok(views.html.index())
  }

  def offline= Action { implicit request =>
    Ok(views.html.offline())
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
      ReversiWebSocketActorFactory.create(out)
    }
  }

  object ReversiWebSocketActorFactory {
    def create(out: ActorRef) = {
      Props(new ReversiWebSocketActor(out))
    }
  }

  class ReversiWebSocketActor(out: ActorRef) extends Actor with Reactor {
    listenTo(gameController)

    def receive = {
      case msg: String =>
        out ! (gameController.toJson)
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