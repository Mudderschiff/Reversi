package de.htwg.se.reversi.controller.controllerComponent

import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.CellInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  def gridSize: Int
  def createEmptyGrid: Unit
  def createNewGrid: Unit
  def save: Unit
  def load: Unit
  def resize(newSize: Int): Unit
  def set(row: Int, col: Int, value: Int): Unit
  //def checkChange(gridnew: GridInterface): Boolean
  def highlight(playerId: Int): Unit
  def score(): (Int, Int)
  def gameStatus: GameStatus
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
