package de.htwg.se.reversi.model.gridComponent

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Turn
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): GridInterface
  def getValidTurns(playerId: Int): List[Turn]
  def reset(row:Int, col:Int): GridInterface
  def createNewGrid: GridInterface
  def size: Int
  def evaluateGame():Int
  def highlight(playerId: Int): GridInterface
  def makeNextTurnRandom(playerId:Int):Grid
  def makeNextTurnKI(playerId: Int):Grid
  def finish(activePlayer: Int): Boolean
  def score(): (Int, Int)
  def checkChange(playerId: Int, row: Int, col: Int): (Boolean, Grid)
}

trait CellInterface {
  def value:Int
  def isSet: Boolean
}