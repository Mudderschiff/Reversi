package de.htwg.se.reversi.model.gridComponent

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Turn
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): GridInterface
  def setTurnRC(playerId: Int, row: Int, col: Int): GridInterface
  def reset(row:Int, col:Int): GridInterface
  def createNewGrid: GridInterface
  def size: Int
  def evaluateGame():Int
  def highlight(playerId: Int): GridInterface
  def getNextTurnR(validTurns:List[Turn]):Turn
  def getNextTurnKI(validTurns:List[Turn], playerId:Int):Turn
  def finish(activePlyer: Int): Boolean
  def score(): (Int, Int)
  def checkChange(gridnew: GridInterface): Boolean
  def unhighlight(): Grid
}

trait CellInterface {
  def value:Int
  def isSet: Boolean
}