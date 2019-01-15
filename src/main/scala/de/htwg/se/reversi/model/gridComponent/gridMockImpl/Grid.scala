package de.htwg.se.reversi.model.gridComponent.gridMockImpl

import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Turn
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

class Grid(var size:Int) extends GridInterface{

  size=1
  def cell(row: Int, col: Int): CellInterface = EmptyCell
  def set(row: Int, col: Int, value: Int): GridInterface = this
  def setTurnRC(playerId: Int, row: Int, col: Int): GridInterface = this
  def reset(row:Int, col:Int): GridInterface = this
  def createNewGrid: GridInterface = this
  def evaluateGame():Int = 0
  def highlight(playerId: Int): GridInterface = this
  def finish(activePlayer: Int): Boolean = false

  override def score(): (Int, Int) = (0,0)
  override def checkChange(playerId: Int, row: Int, col: Int): (Boolean,GridInterface) = (false, this)
  override def getValidTurns(playerId: Int): List[Turn] = List()
  override def makeNextTurnBot(playerId: Int): GridInterface = this
}

object EmptyCell extends CellInterface {
  def value: Int = 0
  def isSet: Boolean = false
}