package de.htwg.se.reversi.controller.controllerComponent.controllerMockImpl

import de.htwg.se.reversi.controller.controllerComponent.{ ControllerInterface, GameStatus }
import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.{ CellInterface, GridInterface }
import de.htwg.se.reversi.model.gridComponent.gridMockImpl.Grid
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._

class Controller(var grid: GridInterface) extends ControllerInterface {
  override def gridSize: Int = ???

  override def createEmptyGrid(): Unit = ???

  override def createNewGrid(): Unit = ???

  override def save(): Unit = ???

  override def load(): Unit = ???

  override def resize(newSize: Int): Unit = ???

  override def set(row: Int, col: Int, value: Int): Unit = ???

  override def score(): (Int, Int) = ???

  override def gameStatus: GameStatus = ???

  override def gridToString: String = ???

  override def getActivePlayer(): Int = ???

  override def finish(): Unit = ???

  override def evaluateGame(): Int = ???

  override def botState(): Boolean = ???

  override def enableBot(): Unit = ???

  override def disableBot(): Unit = ???

  override def bot(): Unit = ???

  override def statusText: String = ???

  override def cell(row: Int, col: Int): CellInterface = ???
}
