package de.htwg.se.reversi.controller.controllerComponent

import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.CellInterface

import scala.collection.mutable.ListBuffer

trait ControllerInterface {
  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): Unit
  //def setTurn(turn:Turn, value:Int): Unit
  def setTurnIndex(playerId: Int, index: Int): Unit
  def reset(row:Int, col:Int): Unit
  def createNewGrid: Unit
  def size: Int
  //def getValidTurns(playerId:Int):List[Turn]
  def evaluateGame():Int
  //def setHighlight(turn:Turn):Unit
  def highlight(playerId: Int): Unit
  //def getNextTurnR(validTurns:List[Turn]):Turn
  //def getNextTurnKI(validTurns:List[Turn], playerId:Int):Turn

}

trait ControllerIoInterface {
  def setTurnRC(playerId: Int, row: Int, col: Int)
}