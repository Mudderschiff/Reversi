package de.htwg.se.reversi.controller.controllerComponent

import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.CellInterface

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher {
  def gridSize: Int

  def createEmptyGrid(): Unit

  def createNewGrid(): Unit

  def save(): Unit

  def load(): Unit

  def resize(newSize: Int): Unit

  def set(row: Int, col: Int, value: Int): Unit

  def score(): (Int, Int)

  def gameStatus: GameStatus

  def gridToString: String

  def getActivePlayer(): Int

  def finish(): Unit

  def evaluateGame(): Int

  def botState(): Boolean

  def enableBot(): Unit

  def disableBot(): Unit

  def bot(): Unit

  def statusText: String

  def cell(row: Int, col: Int): CellInterface

  def undo: Unit

  def redo: Unit
}

class CellChanged extends Event

case class GridSizeChanged(newSize: Int) extends Event

class Finished extends Event

class BotStatus extends Event