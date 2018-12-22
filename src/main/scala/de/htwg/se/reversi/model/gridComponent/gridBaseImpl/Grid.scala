package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.collection.mutable.ListBuffer

case class Grid(private val cells:Matrix[Cell]) extends GridInterface {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))
  def row(row:Int):House = House(cells.rows(row))
  def col(col:Int):House = House(cells.rows.map(row=>row(col)))
  def reset(row:Int, col:Int):Grid = copy(cells.replaceCell(row, col, Cell(0)))

  //TODO: Copy old Grid or return Grid
  def highlight(playerId: Int): GridInterface = {
    var grid = new Grid(this.size).createNewGrid
    getValidTurns(playerId: Int).foreach(turn => grid = grid.sethighlight(turn))
    grid
  }

  def sethighlight(turn:Turn):GridInterface = {
    var grid = this
    turn.dir match {
      case Direction.Up => grid = grid.set(turn.toRow, turn.fromCol,3)
      case Direction.Left => grid = grid.set(turn.fromRow, turn.toCol,3)
      case Direction.Down => grid = grid.set(turn.toRow, turn.fromCol,3)
      case Direction.Right => grid = grid.set(turn.fromRow, turn.toCol, 3)
      case Direction.UpRight => grid = grid.set(turn.toRow,turn.toCol,3)
      case Direction.UpLeft => grid = grid.set(turn.toRow,turn.toCol,3)
      case Direction.DownRight => grid = grid.set(turn.toRow,turn.toCol,3)
      case Direction.DownLeft => grid = grid.set(turn.toRow,turn.toCol,3)

    }
    grid
  }

/*
  def indexToRowCol(index: Int): (Int, Int) = {
    val r = index / size
    val c = index % size
    (r, c)
  }
*/

  def evaluateGame():Int = {
    var black, white = 0
      this.cells.rows.foreach(coll => coll.foreach(cell => {
        if (cell.value.equals(2)) {
          black += 1
        }
      }))
      this.cells.rows.foreach(coll => coll.foreach(cell => {
        if (cell.value.equals(1)) {
          white += 1
        }
      }))
    if (white > black) 1 else if (black > white) 2 else 0
  }

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

  override def createNewGrid: GridInterface = (new GridCreator).createGrid(size)

  def setTurn(turn:Turn, value:Int):GridInterface = {
    var grid = this
    turn.dir match {
      case Direction.Down => for (i <- turn.fromRow to turn.toRow) grid = grid.set(i, turn.fromCol,value)
      case Direction.Up => for (i <- turn.toRow to turn.fromRow) grid = grid.set(i, turn.fromCol, value)
      case Direction.Left => for(i <- turn.toCol to turn.fromCol) grid = grid.set(turn.fromRow, i ,value)
      case Direction.Right => for(i <- turn.fromCol to turn.toCol)grid = grid.set(turn.fromRow, i, value)
      case Direction.UpRight =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i >= turn.toRow && j <= turn.toCol) {
          grid = grid.set(i,j,value)
          i -= 1
          j += 1
        }
      case Direction.UpLeft =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i >= turn.toRow && j >= turn.toCol) {
          grid = grid.set(i,j,value)
          i -= 1
          j -= 1
        }
      case Direction.DownRight =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i <= turn.toRow && j <= turn.toCol) {
          grid = grid.set(i,j,value)
          i += 1
          j += 1
        }
      case Direction.DownLeft =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i <= turn.toRow && j >= turn.toCol) {
          grid = grid.set(i,j,value)
          i += 1
          j -= 1
        }
    }
    grid
  }

  def getValidTurns(playerId: Int): List[Turn] = {
    var reval = new ListBuffer[Turn]

    if(playerId != 2 && playerId != 1) {
      return reval.toList
    }

    for(row <- 0 until size) {
      for(col <- 0 until size) {
        if(this.cell(row,col).value == playerId) {
          lookup(row, col, playerId, this).foreach(i => reval += i)
          lookdown(row, col, playerId, this).foreach(i => reval += i)
          lookleft(row, col, playerId, this).foreach(i => reval += i)
          lookright(row, col, playerId, this).foreach(i => reval += i)
          lookupright(row, col, playerId, this).foreach(i => reval += i)
          lookdownright(row, col, playerId, this).foreach(i => reval += i)
          lookupleft(row, col, playerId, this).foreach(i => reval += i)
          lookdownleft(row, col, playerId, this).foreach(i => reval += i)
        }
      }
    }
    reval.toList
  }

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1) return None

    var up = row-1

    if(grid.cell(up,col).value != playerId && grid.cell(up,col).value != 0) {
      while (up > 0) {
        up -= 1
        if(grid.cell(up,col).value == 0) return Some(Turn(row,col,up,col,Direction.Up))
      }
      None
    }
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1) return None

    var down = row + 1

    if(grid.cell(down,col).value != playerId && grid.cell(down,col).value != 0) {
      while (down < grid.size - 1) {
        down += 1
        if(grid.cell(down,col).value == 0) return Some(Turn(row,col,down,col,Direction.Down))
      }
      None
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(col == grid.size || col == grid.size - 1) return None

    var right = col + 1

    if(grid.cell(row,right).value != playerId && grid.cell(row,right).value != 0) {
      while (right < grid.size - 1) {
        right += 1
        if(grid.cell(row,right).value == 0) return Some(Turn(row,col,row,right,Direction.Right))
      }
      None
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(col == 0 || col == 1) return None

    var left = col - 1

    if(grid.cell(row,left).value != playerId && grid.cell(row,left).value != 0) {
      while (left > 0) {
        left -= 1
        if(grid.cell(row,left).value == 0) return Some(Turn(row,col,row,left,Direction.Left))
      }
      None
    }
    None
  }

  private def lookupright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1 || col == grid.size || col == grid.size - 1) return None

    var up = row - 1
    var right = col + 1

    if(grid.cell(up,right).value != playerId && grid.cell(up,right).value != 0) {
      while (up > 0 && right < grid.size - 1) {
        up -= 1
        right += 1
        if(grid.cell(up,right).value == 0) return Some(Turn(row,col,up,right,Direction.UpRight))
      }
      None
    }
    None
  }

  private def lookupleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1 || col == 0 || col == 1) return None

    var up = row - 1
    var left = col - 1

    if(grid.cell(up,left).value != playerId && grid.cell(up,left).value != 0) {
      while (up > 0 && left > 0) {
        up -= 1
        left -= 1
        if(grid.cell(up,left).value == 0) return Some(Turn(row,col,up,left, Direction.UpLeft))
      }
      None
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1 || col == grid.size || col == grid.size - 1) return None

    var down = row + 1
    var right = col + 1

    if(grid.cell(down,right).value != playerId && grid.cell(down,right).value != 0) {
      while (down < grid.size - 1 && right < grid.size - 1) {
        down += 1
        right += 1
        if(grid.cell(down,right).value == 0) return Some(Turn(row,col,down,right, Direction.DownRight))
      }
      None
    }
    None
  }

  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1 || col == 0 || col == 1) return None

    var down = row + 1
    var left = col - 1

    if(grid.cell(down,left).value != playerId && grid.cell(down,left).value != 0) {
      while (down < grid.size - 1 && left > 0) {
        down += 1
        left -= 1
        if(grid.cell(down,left).value == 0) return Some(Turn(row,col,down,left,Direction.DownLeft))
      }
      None
    }
    None
  }
}

case class House(private val cells:Vector[Cell]) {
  def cell(index:Int):Cell = cells(index)
}

case class Turn(var fromRow:Int, var fromCol:Int, var toRow:Int, var toCol:Int, var dir:Direction.Value)

object Direction extends Enumeration {
  type Direction = Value
  val Up, Down, Left, Right, UpRight, UpLeft, DownRight, DownLeft = Value
}
