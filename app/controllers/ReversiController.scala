package controllers

import javax.inject._

import play.api.mvc._
import de.htwg.se.reversi.Reversi
import de.htwg.se.reversi.controller.controllerComponent.GameStatus

@Singleton
class ReversiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val gameController = Reversi.controller
  def reversiAsText =  gameController.gridToString + GameStatus.message(gameController.gameStatus)

  def about= Action {
    Ok(views.html.index())
  }


  def reversi = Action {
    Ok(reversiAsText)
  }

}