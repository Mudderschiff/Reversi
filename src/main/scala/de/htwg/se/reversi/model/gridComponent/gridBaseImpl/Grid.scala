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
    if (grid == newgrid) (false,grid) else (true,newgrid)
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

  def setTurnD(turn: Turn, value: Int, i: Int, grid: Grid): Grid = {
    if(i > turn.toRow) {
      grid
    }
    else {
      val newi = i+1
      setTurnD(turn, value, newi, grid.set(i, turn.fromCol, value))
    }
  }

  def setTurnU(turn: Turn, value: Int, i: Int, grid: Grid): Grid = {
    if(i < turn.toRow) {
      grid
    }
    else {
      val newi = i-1
      setTurnU(turn, value, newi, grid.set(i, turn.fromCol, value))
    }
  }

  def setTurnL(turn: Turn, value: Int, i: Int, grid: Grid): Grid = {
    if(i < turn.toCol) {
      grid
    }
    else {
      val newi = i-1
      setTurnL(turn, value, newi, grid.set(turn.fromRow, i, value))
    }
  }

  def setTurnR(turn: Turn, value: Int, i: Int, grid: Grid): Grid = {
    if(i > turn.toCol) {
      grid
    }
    else {
      val newi = i+1
      setTurnR(turn, value, newi, grid.set(turn.fromRow, i, value))
    }
  }

  def setTurnUR(turn: Turn, value: Int, i: Int, j: Int, grid: Grid): Grid = {
    if(i < turn.toRow) {
      grid
    }
    else {
      val newi = i - 1
      val newj = j + 1
      setTurnUR(turn, value, newi, newj, grid.set(i, j, value))
    }
  }

  def setTurnUL(turn: Turn, value: Int, i: Int, j: Int, grid: Grid): Grid = {
    if(i < turn.toRow) {
      grid
    }
    else {
      val newi = i - 1
      val newj = j - 1
      setTurnUL(turn, value, newi, newj, grid.set(i, j, value))
    }
  }

  def setTurnDR(turn: Turn, value: Int, i: Int, j: Int, grid: Grid): Grid = {
    if(i > turn.toRow) {
      grid
    }
    else {
      val newi = i + 1
      val newj = j + 1
      setTurnDR(turn, value, newi, newj, grid.set(i, j, value))
    }
  }

  def setTurnDL(turn: Turn, value: Int, i: Int, j: Int, grid: Grid): Grid = {
    if(i > turn.toRow) {
      grid
    }
    else {
      val newi = i + 1
      val newj = j - 1
      setTurnDL(turn, value, newi, newj, grid.set(i, j, value))
    }
  }

  def setTurn(turn: Turn, value: Int): Grid = {
    turn.dir match {
      case Direction.Down => setTurnD(turn, value, turn.fromRow, this)
      case Direction.Up => setTurnU(turn, value, turn.fromRow, this)
      case Direction.Left => setTurnL(turn, value, turn.fromCol, this)
      case Direction.Right => setTurnR(turn, value, turn.fromCol, this)
      case Direction.UpRight => setTurnUR(turn, value, turn.fromRow, turn.fromCol, this)
      case Direction.UpLeft => setTurnUL(turn, value, turn.fromRow, turn.fromCol, this)
      case Direction.DownRight => setTurnDR(turn, value, turn.fromRow, turn.fromCol, this)
      case Direction.DownLeft => setTurnDL(turn, value, turn.fromRow, turn.fromCol, this)
    }
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

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0) return None
    val up = row - 1
    if (grid.cell(up, col).value != playerId && grid.cell(up, col).value != 0 && grid.cell(up, col).value != 3)
      for {
        up <- up until 0 by -1
      } if (grid.cell(up-1, col).value == playerId) return None else if (grid.cell(up-1, col).value == 0 || grid.cell(up-1, col).value == 3) return Some(Turn(row, col, up-1, col, Direction.Up))
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1) return None
    val down = row + 1
    if (grid.cell(down, col).value != playerId && grid.cell(down, col).value != 0 && grid.cell(down, col).value != 3) {
      for {
        down <- down until grid.size -1
      } if (grid.cell(down+1, col).value == playerId) return None else if (grid.cell(down+1, col).value == 0 || grid.cell(down+1, col).value == 3) return Some(Turn(row, col, down+1, col, Direction.Down))
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == grid.size - 1) return None

    val right = col + 1

    if (grid.cell(row, right).value != playerId && grid.cell(row, right).value != 0 && grid.cell(row, right).value != 3) {
      for {
        right <- right until grid.size - 1
      }if (grid.cell(row, right+1).value == playerId) return None else  if (grid.cell(row, right+1).value == 0 || grid.cell(row, right+1).value == 3) return Some(Turn(row, col, row, right+1, Direction.Right))
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == 0) return None

    val left = col - 1

    if (grid.cell(row, left).value != playerId && grid.cell(row, left).value != 0 && grid.cell(row, left).value != 3) {
      for {
        left <- left until 0 by -1
      } if (grid.cell(row, left -1).value == playerId) return None else if (grid.cell(row, left-1).value == 0 || grid.cell(row, left-1).value == 3) return Some(Turn(row, col, row, left-1, Direction.Left))
    }
    None
  }

  private def lookupright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0 || col == grid.size - 1) return None

    val up = row - 1
    val right = col + 1

    if (grid.cell(up, right).value != playerId && grid.cell(up, right).value != 0 && grid.cell(up, right).value != 3) {
      for {
        up <- up until 0 by -1
        right <- right until grid.size -1
      } if (grid.cell(up-1, right+1).value == playerId) return None else if (grid.cell(up-1, right+1).value == 0 || grid.cell(up-1, right+1).value == 3) return Some(Turn(row, col, up-1, right+1, Direction.UpRight))
    }
    None
  }

  private def lookupleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0 || col == 0) return None

    val up = row - 1
    val left = col - 1

    if (grid.cell(up, left).value != playerId && grid.cell(up, left).value != 0 && grid.cell(up, left).value != 3) {
      for {
        up <- up until 0 by -1
        left <- left until 0 by -1
      } if (grid.cell(up-1, left-1).value == playerId) return None else if (grid.cell(up-1, left-1).value == 0 || grid.cell(up-1, left-1).value == 3) return Some(Turn(row, col, up-1, left-1, Direction.UpLeft))
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == grid.size - 1) return None

    val down = row + 1
    val right = col + 1

    if (grid.cell(down, right).value != playerId && grid.cell(down, right).value != 0 && grid.cell(down, right).value != 3) {
      for {
        down <- down until grid.size -1
        right <- right until grid.size -1
      } if (grid.cell(down+1, right+1).value == playerId) return None else if (grid.cell(down+1, right+1).value == 0 || grid.cell(down+1, right+1).value == 3) return Some(Turn(row, col, down+1, right+1, Direction.DownRight))
    }
    None
  }

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == 0) return None

    val down = row + 1
    val left = col - 1

    if (grid.cell(down, left).value != playerId && grid.cell(down, left).value != 0 && grid.cell(down, left).value != 3) {
      for {
        down <- down until grid.size -1
        left <- left until 0 by -1
      } if (grid.cell(down+1, left-1).value == playerId) return None else if (grid.cell(down+1, left-1).value == 0 || grid.cell(down+1, left-1).value == 3) return Some(Turn(row, col, down+1, left-1, Direction.DownLeft))
    }
    None
  }

  def evaluateGame(): Int = if (score()._2 > score()._1) 1 else if (score()._1 > score()._2) 2 else 0

  def score(): (Int, Int) = {
    val black = for {
      row <- 0 until size
      col <- 0 until size
      if cell(row, col).value.equals(2)
    } yield (row,col)
    val white = for {
      row <- 0 until size
      col <- 0 until size
      if cell(row, col).value.equals(1)
    } yield (row,col)
    (black.size,white.size)
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
