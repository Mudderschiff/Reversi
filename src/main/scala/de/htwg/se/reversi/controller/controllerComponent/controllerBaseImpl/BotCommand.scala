package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.util.Command

class BotCommand(gridInterface: GridInterface, controller: Controller) extends Command {
  var oldgrid = controller.grid
  var botgrid = controller.grid

  override def doStep: Unit = {
    oldgrid = gridInterface
    botgrid = gridInterface.makeNextTurnBot(2)
    controller.grid = botgrid
  }

  override def undoStep: Unit = controller.grid = oldgrid

  override def redoStep: Unit = controller.grid = botgrid
}