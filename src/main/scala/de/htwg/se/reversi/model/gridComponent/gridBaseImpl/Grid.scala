package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.math.sqrt

case class Grid(private val cells:Matrix[Cell]) extends GridInterface {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))
  def row(row:Int):House = House(cells.rows(row))
  def col(col:Int):House = House(cells.rows.map(row=>row(col)))
  def reset(row:Int, col:Int):Grid = copy(cells.replaceCell(row, col, Cell(0)))
  val blocknum: Int = sqrt(size).toInt

  override def toString: String = {
    val lineseparator = ("+-" + ("--" * (size - 1))) + "+\n"
    val line = ("|" + (("x" + "|") * size)) + "\n"
    var box = "\n" + lineseparator + ((line + lineseparator) * size)
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }

  override def createNewGrid: GridInterface = ???

  def getValidCells(playerId: Int): ListBuffer[(Int, Int)] = {
    val grid = this
    var reval = new ListBuffer[(Int, Int)]

    for(row <- 0 to size) {
      for(col <- 0 to size) {

        if(grid.cell(row,col) == playerId) {
          lookup(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookdown(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookleft(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookright(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookupright(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookdownright(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookupleft(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
          lookdownleft(row, col, playerId, grid) match {
            case Some(value) => reval += value
            case None => _
          }
        }
      }
    }
    reval
  }

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == 0 || row == 1) None

    var up = row-1

    if(grid.cell(up,col).value != playerId && grid.cell(up,col).value != 0) {
      while (up > 0) {
        up = up-1
        if(grid.cell(up,col).value == 0) Some((up,col))
      }
      None
    }
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == grid.size || row == grid.size - 1) None

    var down = row + 1

    if(grid.cell(down,col).value != playerId && grid.cell(down,col).value != 0) {
      while (down < grid.size) {
        down = down + 1
        if(grid.cell(down,col).value == 0) Some((down,col))
      }
      None
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(col == grid.size || col == grid.size - 1) None

    var right = col + 1

    if(grid.cell(row,right).value != playerId && grid.cell(row,right).value != 0) {
      while (right < grid.size) {
        right = right + 1
        if(grid.cell(row,right).value == 0) Some((row,right))
      }
      None
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(col == 0 || col == 1) None

    var left = col-1

    if(grid.cell(row,left).value != playerId && grid.cell(row,left).value != 0) {
      while (left > 0) {
        left = left-1
        if(grid.cell(row,left).value == 0) Some((row,left))
      }
      None
    }
    None
  }

  private def lookupright(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == 0 || row == 1 || col == grid.size || col == grid.size - 1) None

    var up = row-1
    var right = col+1

    if(grid.cell(up,right).value != playerId && grid.cell(up,right).value != 0) {
      while (up > 0 && right < grid.size) {
        up = up-1
        right = right+1
        if(grid.cell(up,right).value == 0) Some((up,right))
      }
      None
    }
    None
  }

  private def lookupleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == 0 || row == 1 || col == 0 || col == 1) None

    var up = row-1
    var left = col-1

    if(grid.cell(up,left).value != playerId && grid.cell(up,left).value != 0) {
      while (up > 0 && left > 0) {
        up = up-1
        left = left-1
        if(grid.cell(up,left).value == 0) Some((up,left))
      }
      None
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == grid.size || row == grid.size - 1) None

    var down = row + 1
    var right = col + 1

    if(grid.cell(down,right).value != playerId && grid.cell(down,right).value != 0) {
      while (down < grid.size && right < grid.size) {
        down = down + 1
        right = right+1
        if(grid.cell(down,right).value == 0) Some((down,right))
      }
      None
    }
    None
  }

  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[(Int, Int)] = {
    if(row == grid.size || row == grid.size - 1) None

    var down = row + 1
    var left = col-1

    if(grid.cell(down,left).value != playerId && grid.cell(down,left).value != 0) {
      while (down < grid.size && left > 0) {
        down = down + 1
        left = left-1
        if(grid.cell(down,left).value == 0) Some((down,left))
      }
      None
    }
    None
  }
}

case class House(private val cells:Vector[Cell]) {
  def cell(index:Int):Cell = cells(index)
}