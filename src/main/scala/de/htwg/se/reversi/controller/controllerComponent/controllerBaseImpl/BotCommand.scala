package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.util.Command

class BotCommand(controller: Controller) extends Command {
  var botgrid = controller.grid.makeNextTurnBot(2).highlight(1)
  var oldgrid = controller.grid

  override def doStep: Unit = controller.grid = botgrid

  override def undoStep: Unit = {
    controller.changePlayer()
    controller.grid = oldgrid
  }

  override def redoStep: Unit = {
    controller.changePlayer()
    controller.grid = botgrid
  }
}