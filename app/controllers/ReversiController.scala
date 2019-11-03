package controllers

import javax.inject._

import play.api.mvc._
import de.htwg.se.reversi.Reversi
import de.htwg.se.reversi.controller.controllerComponent.GameStatus

@Singleton
class ReversiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
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


}