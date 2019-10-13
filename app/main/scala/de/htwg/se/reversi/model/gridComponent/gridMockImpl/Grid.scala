package de.htwg.wt.reversi.model.gridComponent.gridMockImpl

import de.htwg.wt.reversi.model.gridComponent.gridBaseImpl.Turn
import de.htwg.wt.reversi.model.gridComponent.{CellInterface, GridInterface}

class Grid(var size: Int) extends GridInterface {

  size = 1

  override def cell(row: Int, col: Int): CellInterface = EmptyCell

  override def set(row: Int, col: Int, value: Int): GridInterface = this

  override def getValidTurns(playerId: Int): List[Turn] = List()

  override def createNewGrid: GridInterface = this

  override def evaluateGame(): Int = 0

  override def highlight(playerId: Int): GridInterface = this

  override def makeNextTurnBot(playerId: Int): GridInterface = this

  override def finish(activePlayer: Int): Boolean = false

  override def score(): (Int, Int) = (0, 0)

  override def checkChange(playerId: Int, row: Int, col: Int): (Boolean, GridInterface) = (false, this)
}

object EmptyCell extends CellInterface {
  def value: Int = 0

  def isSet: Boolean = false
}