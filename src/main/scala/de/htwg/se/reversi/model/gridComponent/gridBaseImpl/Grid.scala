package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.collection.mutable.ListBuffer

case class Grid(private val cells: Matrix[Cell]) extends GridInterface {
  val size: Int = cells.size

  def this(size: Int) = this(new Matrix[Cell](size, Cell(0)))

  def row(row: Int): House = House(cells.rows(row))

  def col(col: Int): House = House(cells.rows.map(row => row(col)))

  def finish(activePlayer: Int): Boolean = getValidTurns(activePlayer).isEmpty

  def highlight(playerId: Int): Grid = recursiveHighlight(getValidTurns(playerId),unHighlight(this))

  def recursiveHighlight(list: List[Turn], grid: Grid): Grid = if (list.nonEmpty) recursiveHighlight(list.drop(1) , grid.copy(grid.setHighlight(list.head).cells)) else grid

  def setHighlight(turn: Turn): Grid = {
    turn.dir match {
      case Direction.Up =>  set(turn.toRow, turn.fromCol, 3)
      case Direction.Left =>  set(turn.fromRow, turn.toCol, 3)
      case Direction.Down => set(turn.toRow, turn.fromCol, 3)
      case Direction.Right => set(turn.fromRow, turn.toCol, 3)
      case Direction.UpRight =>  set(turn.toRow, turn.toCol, 3)
      case Direction.UpLeft =>  set(turn.toRow, turn.toCol, 3)
      case Direction.DownRight =>  set(turn.toRow, turn.toCol, 3)
      case Direction.DownLeft =>  set(turn.toRow, turn.toCol, 3)
    }
  }

  def checkChange(playerId: Int, row: Int, col: Int): (Boolean, Grid) = {
    val grid = unHighlight(this)
    val newgrid = unHighlight(grid.setTurnRC(playerId, row, col))
    val rc = for {
      row <- 0 until size
      col <- 0 until size
      if grid.cell(row, col).value != newgrid.cell(row, col).value
    } yield true
    if (rc.isEmpty) (false,newgrid) else (rc.head, newgrid)
  }

  def unHighlight(grid: Grid): Grid = {
    val rc = for {
      row <- 0 until size
      col <- 0 until size
      if grid.cell(row, col).value == 3
    } yield (row,col)
    unHighlightRecursive(rc ,grid)
  }

  def unHighlightRecursive(indexedSeq: IndexedSeq[(Int,Int)],grid: Grid): Grid = if (indexedSeq.nonEmpty) unHighlightRecursive(indexedSeq.drop(1), grid.copy(grid.reset(indexedSeq.head._1,indexedSeq.head._2).cells)) else grid

  def reset(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, Cell(0)))

  def setTurnRC(playerId: Int, row: Int, col: Int): Grid =  {
    val map = getValidTurns(playerId).filter(turn => turn.toCol == col && turn.toRow == row)
    recursiveSetTurnRC(map,this,playerId)
  }
  def recursiveSetTurnRC(list: List[Turn], grid: Grid, playerId: Int): Grid = if (list.nonEmpty) recursiveSetTurnRC(list.drop(1), grid.copy(grid.setTurn(list.head, playerId).cells),playerId) else grid

  def setTurn(turn: Turn, value: Int): Grid = {
    var grid = this
    turn.dir match {
      case Direction.Down => for (i <- turn.fromRow to turn.toRow) grid = grid.set(i, turn.fromCol, value)
      case Direction.Up => for (i <- turn.toRow to turn.fromRow) grid = grid.set(i, turn.fromCol, value)
      case Direction.Left => for (i <- turn.toCol to turn.fromCol) grid = grid.set(turn.fromRow, i, value)
      case Direction.Right => for (i <- turn.fromCol to turn.toCol) grid = grid.set(turn.fromRow, i, value)
      case Direction.UpRight =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i >= turn.toRow && j <= turn.toCol) {
          grid = grid.set(i, j, value)
          i -= 1
          j += 1
        }
      case Direction.UpLeft =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i >= turn.toRow && j >= turn.toCol) {
          grid = grid.set(i, j, value)
          i -= 1
          j -= 1
        }
      case Direction.DownRight =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i <= turn.toRow && j <= turn.toCol) {
          grid = grid.set(i, j, value)
          i += 1
          j += 1
        }
      case Direction.DownLeft =>
        var (i, j) = (turn.fromRow, turn.fromCol)
        while (i <= turn.toRow && j >= turn.toCol) {
          grid = grid.set(i, j, value)
          i += 1
          j -= 1
        }
    }
    grid
  }

  def set(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(value)))

  def getValidTurns(playerId: Int): List[Turn] = {
    if (playerId != 2 && playerId != 1) return Nil

    var retVal = new ListBuffer[Turn]

    for {
      row <- 0 until size
      col <- 0 until size
    } if (this.cell(row, col).value == playerId) {
      lookup(row, col, playerId, this) foreach(retVal += _)
      lookdown(row, col, playerId, this) foreach(retVal += _)
      lookleft(row, col, playerId, this) foreach(retVal += _)
      lookright(row, col, playerId, this) foreach(retVal += _)
      lookupright(row, col, playerId, this) foreach(retVal += _)
      lookdownright(row, col, playerId, this) foreach(retVal += _)
      lookupleft(row, col, playerId, this) foreach(retVal += _)
      lookdownleft(row, col, playerId, this) foreach(retVal += _)
    }
    retVal.toList
  }

 /* private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0) return None
    val up = row - 1
    if (grid.cell(up, col).value != playerId && grid.cell(up, col).value != 0 && grid.cell(up, col).value != 3) {
      for {
        up1 <- up until 0 by -1
      } if (grid.cell(up1, col).value != playerId) return None if (grid.cell(up1, col).value == 0 || grid.cell(up1, col).value == 3) return Some(Turn(row, col, up1, col, Direction.Up))
    }
    return None
  }*/

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0) return None

    var up = row - 1

    if (grid.cell(up, col).value != playerId && grid.cell(up, col).value != 0 && grid.cell(up, col).value != 3) {
      while (up > 0) {
        up -= 1
        if (grid.cell(up, col).value == 0 || grid.cell(up, col).value == 3) return Some(Turn(row, col, up, col, Direction.Up))
      }
    }
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1) return None

    var down = row + 1

    if (grid.cell(down, col).value != playerId && grid.cell(down, col).value != 0 && grid.cell(down, col).value != 3) {
      while (down < grid.size - 1) {
        down += 1
        if (grid.cell(down, col).value == playerId) return None
        if (grid.cell(down, col).value == 0 || grid.cell(down, col).value == 3) return Some(Turn(row, col, down, col, Direction.Down))
      }
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == grid.size - 1) return None

    var right = col + 1

    if (grid.cell(row, right).value != playerId && grid.cell(row, right).value != 0 && grid.cell(row, right).value != 3) {
      while (right < grid.size - 1) {
        right += 1
        if (grid.cell(row, right).value == playerId) return None
        if (grid.cell(row, right).value == 0 || grid.cell(row, right).value == 3) return Some(Turn(row, col, row, right, Direction.Right))
      }
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == 0) return None

    var left = col - 1

    if (grid.cell(row, left).value != playerId && grid.cell(row, left).value != 0 && grid.cell(row, left).value != 3) {
      while (left > 0) {
        left -= 1
        if (grid.cell(row, left).value == playerId) return None
        if (grid.cell(row, left).value == 0 || grid.cell(row, left).value == 3) return Some(Turn(row, col, row, left, Direction.Left))
      }
    }
    None
  }

  private def lookupright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0 || col == grid.size - 1) return None

    var up = row - 1
    var right = col + 1

    if (grid.cell(up, right).value != playerId && grid.cell(up, right).value != 0 && grid.cell(up, right).value != 3) {
      while (up > 0 && right < grid.size - 1) {
        up -= 1
        right += 1
        if (grid.cell(up, right).value == playerId) return None
        if (grid.cell(up, right).value == 0 || grid.cell(up, right).value == 3) return Some(Turn(row, col, up, right, Direction.UpRight))
      }
    }
    None
  }

  private def lookupleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0 || col == 0) return None

    var up = row - 1
    var left = col - 1

    if (grid.cell(up, left).value != playerId && grid.cell(up, left).value != 0 && grid.cell(up, left).value != 3) {
      while (up > 0 && left > 0) {
        up -= 1
        left -= 1
        if (grid.cell(up, left).value == playerId) return None
        if (grid.cell(up, left).value == 0 || grid.cell(up, left).value == 3) return Some(Turn(row, col, up, left, Direction.UpLeft))
      }
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == grid.size - 1) return None

    var down = row + 1
    var right = col + 1

    if (grid.cell(down, right).value != playerId && grid.cell(down, right).value != 0 && grid.cell(down, right).value != 3) {
      while (down < grid.size - 1 && right < grid.size - 1) {
        down += 1
        right += 1
        if (grid.cell(down, right).value == playerId) return None
        if (grid.cell(down, right).value == 0 || grid.cell(down, right).value == 3) return Some(Turn(row, col, down, right, Direction.DownRight))
      }
    }
    None
  }

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == 0) return None

    var down = row + 1
    var left = col - 1

    if (grid.cell(down, left).value != playerId && grid.cell(down, left).value != 0 && grid.cell(down, left).value != 3) {
      while (down < grid.size - 1 && left > 0) {
        down += 1
        left -= 1
        if (grid.cell(down, left).value == playerId) return None
        if (grid.cell(down, left).value == 0 || grid.cell(down, left).value == 3) return Some(Turn(row, col, down, left, Direction.DownLeft))
      }
    }
    None
  }

  def evaluateGame(): Int = if (score()._2 > score()._1) 1 else if (score()._1 > score()._2) 2 else 0

  def score(): (Int, Int) = {
    var (black, white) = (0, 0)
    for {
      row <- 0 until size
      col <- 0 until size
    } if (cell(row, col).value.equals(2)) black += 1 else if (cell(row, col).value.equals(1)) white += 1
    (black, white)
  }


  override def createNewGrid: GridInterface = (new GridCreator).createGrid(size)

  override def makeNextTurnBot(playerId: Int): GridInterface = (new Bot).makeTurn(this, playerId)

  override def toString: String = {
    val row, col = StringBuilder.newBuilder
    row.append("\n")
    for {i <- 0 until size} if (i == 0) row.append("  " + i.toString) else row.append(" " + i.toString)
    row.append("\n")

    val lineSeparator = (" +-" + ("--" * (size - 1))) + "+\n"
    val line = ("|" + (("x" + "|") * size)) + "\n"
    for {i <- 0 until size} col.append(i.toString + line + lineSeparator)

    var box = row + lineSeparator + col
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }
}
