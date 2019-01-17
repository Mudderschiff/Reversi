package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._

import de.htwg.se.reversi.util.Command


class SetCommand(playerId: Int, row: Int, col: Int, controller: Controller) extends Command {
  var oldgrid = controller.grid


  override def doStep: Unit = {
    oldgrid = controller.grid
    val tuple = controller.grid.checkChange(playerId, row, col)
    if (tuple._1) {
      if (playerId == 1) {
        controller.grid = tuple._2.highlight(2)
        controller.gameStatus = SET_Player1
      } else {
        controller.grid = tuple._2.highlight(1)
        controller.gameStatus = SET_Player2
      }
      controller.changePlayer()
    }
  }


  override def undoStep: Unit = controller.grid = oldgrid

  override def redoStep: Unit = {
    oldgrid = controller.grid
    val tuple = controller.grid.checkChange(playerId, row, col)
    if (tuple._1) {
      if (playerId == 1) {
        controller.grid = tuple._2.highlight(2)
        controller.gameStatus = SET_Player1
      } else {
        controller.grid = tuple._2.highlight(1)
        controller.gameStatus = SET_Player2
      }
      controller.changePlayer()
    }
  }
}

