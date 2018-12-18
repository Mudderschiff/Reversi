package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus

case class GridCreator() {

  def createGrid(size:Int): Either[GameStatus, Grid] = {
    if(size % 2 != 0) {
      Left(GameStatus.OddNumber)
    }
    val grid = new Grid(size)
    Right(initGrid(grid))

  }

  private def initGrid(grid: Grid): Grid = {
    grid.set(grid.size / 2 - 1, grid.size / 2 - 1, 1).set(grid.size / 2 - 1, grid.size / 2, 2).set(grid.size / 2, grid.size / 2 - 1, 2).set(grid.size / 2, grid.size / 2, 1)

  }

}
