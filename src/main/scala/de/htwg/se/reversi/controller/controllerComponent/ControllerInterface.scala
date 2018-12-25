package de.htwg.se.reversi.controller.controllerComponent

import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.CellInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  def gridSize: Int
  def isSet(row: Int, col: Int): Boolean
  def createEmptyGrid: Unit
  def createNewGrid: Unit
  def save: Unit
  def load: Unit
  def gameStatus: GameStatus
  def resize(newSize: Int): Unit
  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): Unit
  //def setTurn(turn:Turn, value:Int): Unit
  def setTurnIndex(playerId: Int, index: Int): Unit
  def reset(row:Int, col:Int): Unit
  def statusText: String
  //def size: Int
  def setTurnRC(playerId: Int, row: Int, col: Int)
  //def getValidTurns(playerId:Int):List[Turn]
  def evaluateGame():Int
  //def setHighlight(turn:Turn):Unit
  def highlight(playerId: Int): Unit
  //def getNextTurnR(validTurns:List[Turn]):Turn
  //def getNextTurnKI(validTurns:List[Turn], playerId:Int):Turn
  def gridToString: String

}
/*
trait ControllerIoInterface {
  def setTurnRC(playerId: Int, row: Int, col: Int)
}*/

import scala.swing.event.Event

class CellChanged extends Event

case class GridSizeChanged(newSize: Int) extends Event

class CandidatesChanged extends Event
