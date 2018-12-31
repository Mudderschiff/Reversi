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
  def highlight(playerId: Int): Unit
  def score(): (Int, Int)
  def gameStatus: GameStatus
  def gridToString: String
  def getActivePlayer(): Int
  def finish: Unit
  def evaluateGame(): Int
  def botstate(): Boolean
  def enableBot(): Unit
  def disableBot(): Unit
  def bot: Unit
  def statusText: String
  def cell(row: Int, col: Int): CellInterface
  def cellToString(row: Int, col: Int): String
}

import scala.swing.event.Event

class CellChanged extends Event

case class GridSizeChanged(newSize: Int) extends Event

class CandidatesChanged extends Event

class Finished extends Event

class BotStatus extends Event