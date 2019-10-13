package de.htwg.wt.reversi.controller.controllerComponent.controllerMockImpl

import de.htwg.wt.reversi.controller.controllerComponent.GameStatus.{GameStatus, _}
import de.htwg.wt.reversi.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.wt.reversi.model.gridComponent.gridMockImpl.Grid
import de.htwg.wt.reversi.model.gridComponent.{CellInterface, GridInterface}

class Controller(var grid: GridInterface) extends ControllerInterface {
  grid = new Grid(1)

  override def gridSize: Int = 1

  override def createEmptyGrid(): Unit = {}

  override def createNewGrid(): Unit = {}

  override def save(): Unit = {}

  override def load(): Unit = {}

  override def resize(newSize: Int): Unit = {}

  override def set(row: Int, col: Int, value: Int): Unit = {}

  override def score(): (Int, Int) = (0, 0)

  override def gridToString: String = grid.toString

  override def getActivePlayer(): Int = 1

  override def finish(): Unit = {}

  override def evaluateGame(): Int = 0

  override def botState(): Boolean = false

  override def enableBot(): Unit = {}

  override def disableBot(): Unit = {}

  override def bot(): Unit = {}

  override def statusText: String = GameStatus.message(gameStatus)

  override def gameStatus: GameStatus = IDLE

  override def cell(row: Int, col: Int): CellInterface = grid.cell(row, col)

  override def undo: Unit = {}

  override def redo: Unit = {}

  override def deleteGridById(id: Int): Boolean = false

  override def getAllGrids: List[(Int, Int, String)] = List()

  override def getGridById(id: Int): (Int, Int, String) = (1,1, grid.toString)

  def saveGrid(grid: String, player: Int): Unit = {}
}
