package de.htwg.se.reversi.model.gridComponent

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Turn

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): GridInterface
  def setTurn(turn:Turn, value:Int): GridInterface
  def reset(row:Int, col:Int): GridInterface
  def createNewGrid: GridInterface
  def size: Int
  def getValidTurns(playerId:Int):List[Turn]
  def evaluateGame():Int
  def sethighlight(turn:Turn):GridInterface
  def highlight(playerId: Int): GridInterface
  //def highlight(playerId: Int): GridInterface

  //def isfinished:Boolean

}

trait CellInterface {
  def value:Int
  def isSet: Boolean
}