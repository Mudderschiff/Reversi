package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class GridCreator() {
  def createGrid(size: Int): Grid = if (size % 2 != 0) new Grid(size) else initGrid(new Grid(size))

  private def initGrid(grid: Grid): Grid = {
    grid.set(grid.size / 2 - 1, grid.size / 2 - 1, 1).set(grid.size / 2 - 1, grid.size / 2, 2).set(grid.size / 2, grid.size / 2 - 1, 2).set(grid.size / 2, grid.size / 2, 1)
  }
}
