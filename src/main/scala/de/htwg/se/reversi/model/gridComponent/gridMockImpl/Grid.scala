package de.htwg.se.reversi.model.gridComponent.gridMockImpl

import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}

class Grid(var size:Int) extends GridInterface{

  size=1
  def cell(row: Int, col: Int): CellInterface = EmptyCell
  def set(row: Int, col: Int, value: Int): GridInterface = this
  def setTurnRC(playerId: Int, row: Int, col: Int): GridInterface = this
  def reset(row:Int, col:Int): GridInterface = this
  def createNewGrid: GridInterface = this
  def evaluateGame():Int = 0
  def highlight(playerId: Int): GridInterface = this
  //should be removed
  def getNextTurnR(validTurns:List[Turn]):Turn = Turn(0,0,0,0,0)
  def getNextTurnKI(validTurns:List[Turn], playerId:Int):Turn = Turn(0,0,0,0,0)
  def finish: Boolean = false
}

object EmptyCell extends CellInterface {
  def value: Int = 0
  def isSet: Boolean = false
}