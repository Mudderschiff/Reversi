package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class GridCreator() {

  def createGrid(size:Int): Grid = {
    if(size % 2 != 0 || size < 3) {
      //Todo: Wie geben wir diesne Error schön zurück ohne Fehler zu erzeugen
      throw new Exception("Die Zahl muss gerade und größer 2 sein!")
    }

    val grid = new Grid(size)

    initGrid(grid)

  }


  private def initGrid(grid: Grid): Grid = {
    grid.set(grid.size / 2 - 1, grid.size / 2 - 1, 1).set(grid.size / 2 - 1, grid.size / 2, 2).set(grid.size / 2, grid.size / 2 - 1, 2).set(grid.size / 2, grid.size / 2, 1)

  }

}
