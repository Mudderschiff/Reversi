package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.util.Command

class SetCommand(row: Int, col: Int, playerID: Int, controller: Controller) extends Command {
  override def doStep: Unit = controller.grid = controller.grid.setTurnRC(playerID,row, col)
}

