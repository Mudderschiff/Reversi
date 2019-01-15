package de.htwg.se.reversi.controller.controllerComponent.controllerMockImpl

import de.htwg.se.reversi.controller.controllerComponent.{ ControllerInterface, GameStatus }
import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.{ CellInterface, GridInterface }
import de.htwg.se.reversi.model.gridComponent.gridMockImpl.Grid
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._

class Controller(var grid: GridInterface) extends ControllerInterface {
  grid = new Grid(1)
  override def gridSize: Int = 1

  override def createEmptyGrid(): Unit = {}

  override def createNewGrid(): Unit = {}

  override def save(): Unit = {}

  override def load(): Unit = {}

  override def resize(newSize: Int): Unit = {}

  override def set(row: Int, col: Int, value: Int): Unit = {}

  override def score(): (Int, Int) = (0,0)

  override def gameStatus: GameStatus = IDLE

  override def gridToString: String = grid.toString

  override def getActivePlayer(): Int = getActivePlayer()

  override def finish(): Unit = {}

  override def evaluateGame(): Int = 0

  override def botState(): Boolean = false

  override def enableBot(): Unit = {}

  override def disableBot(): Unit = {}

  override def bot(): Unit = {}

  override def statusText: String = GameStatus.message(gameStatus)

  override def cell(row: Int, col: Int): CellInterface = grid.cell(row, col)
}
