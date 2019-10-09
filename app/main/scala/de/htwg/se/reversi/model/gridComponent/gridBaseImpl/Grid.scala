package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

case class Grid(private val cells: Matrix[Cell]) extends GridInterface {
  val size: Int = cells.size

  def this(size: Int) = this(new Matrix[Cell](size, Cell(0)))

  def row(row: Int): House = House(cells.rows(row))

  def col(col: Int): House = House(cells.rows.map(row => row(col)))

  def finish(activePlayer: Int): Boolean = getValidTurns(activePlayer).isEmpty

  def highlight(playerId: Int): Grid = recursiveHighlight(getValidTurns(playerId), unHighlight())

  def recursiveHighlight(list: List[Turn], grid: Grid): Grid = if (list.nonEmpty) recursiveHighlight(list.drop(1),
    grid.setHighlight(list.head)) else grid

  def unHighlight(): Grid = unHighlightRecursive(rowcol(3), this)

  def unHighlightRecursive(indexedSeq: IndexedSeq[(Int, Int)], grid: Grid): Grid = if (indexedSeq.nonEmpty)
    unHighlightRecursive(indexedSeq.drop(1), grid.reset(indexedSeq.head._1, indexedSeq.head._2)) else grid

  def reset(row: Int, col: Int): Grid = copy(cells.replaceCell(row, col, Cell(0)))

  def set(row: Int, col: Int, value: Int): Grid = copy(cells.replaceCell(row, col, Cell(value)))

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def evaluateGame(): Int = if (score()._2 > score()._1) 1 else if (score()._1 > score()._2) 2 else 0

  def score(): (Int, Int) = (rowcol(2).size,rowcol(1).size)

  def rowcol(guard: Int): IndexedSeq[(Int,Int)] = {
    for {
      row <- 0 until size
      col <- 0 until size
      if cell(row, col).value == guard
    } yield (row,col)
  }

  def setHighlight(turn: Turn): Grid = {
    turn.dir match {
      case Direction.Up => set(turn.toRow, turn.fromCol, 3)
      case Direction.Left => set(turn.fromRow, turn.toCol, 3)
      case Direction.Down => set(turn.toRow, turn.fromCol, 3)
      case Direction.Right => set(turn.fromRow, turn.toCol, 3)
      case Direction.UpRight => set(turn.toRow, turn.toCol, 3)
      case Direction.UpLeft => set(turn.toRow, turn.toCol, 3)
      case Direction.DownRight => set(turn.toRow, turn.toCol, 3)
      case Direction.DownLeft => set(turn.toRow, turn.toCol, 3)
    }
  }

  def checkChange(playerId: Int, row: Int, col: Int): (Boolean, Grid) = {
    val grid = unHighlight()
    val newgrid = grid.setTurnRC(playerId, row, col).unHighlight()
    if (grid == newgrid) (false, grid) else (true, newgrid)
  }

  def setTurnRC(playerId: Int, row: Int, col: Int): Grid = {
    val map = getValidTurns(playerId) filter (turn => turn.toCol == col && turn.toRow == row)
    recursiveSetTurnRC(map, this, playerId)
  }

  def recursiveSetTurnRC(list: List[Turn], grid: Grid, playerId: Int): Grid = if (list.nonEmpty)
    recursiveSetTurnRC(list.drop(1), grid.setTurn(list.head, playerId), playerId) else grid

  def setTurn(turn: Turn, value: Int): Grid = {
    turn.dir match {
      case Direction.Down => setTurnUpDown(turn.fromRow, turn.toRow, turn.fromCol, value, this)
      case Direction.Up => setTurnUpDown(turn.toRow, turn.fromRow, turn.fromCol, value, this)
      case Direction.Left => setTurnLeftRight(turn.toCol, turn.fromCol, turn.fromRow, value, this)
      case Direction.Right => setTurnLeftRight(turn.fromCol, turn.toCol, turn.fromRow, value, this)
      case Direction.UpRight =>
        val map = (turn.fromRow to turn.toRow by -1).zip(turn.fromCol to turn.toCol)
        setTurnRest((turn.fromRow to turn.toRow by -1).zip(turn.fromCol to turn.toCol), value, this)
      case Direction.UpLeft =>
        val map = (turn.fromRow to turn.toRow by -1).zip(turn.fromCol to turn.toCol by -1)
        setTurnRest(map, value, this)
      case Direction.DownRight =>
        val map = (turn.fromRow to turn.toRow).zip(turn.fromCol to turn.toCol)
        setTurnRest(map, value, this)
      case Direction.DownLeft =>
        val map = (turn.fromRow to turn.toRow).zip(turn.fromCol to turn.toCol by -1)
        setTurnRest(map, value, this)
    }
  }

  def setTurnUpDown(beginn: Int, end: Int, col: Int, playerId: Int, grid: Grid): Grid = {
    if (beginn <= end) {
      val newgrid = grid.set(beginn, col, playerId)
      setTurnUpDown(beginn + 1, end, col, playerId, newgrid)
    } else grid
  }

  def setTurnLeftRight(beginn: Int, end: Int, row: Int, playerId: Int, grid: Grid): Grid = {
    if (beginn <= end) {
      val newgrid = grid.set(row, beginn, playerId)
      setTurnLeftRight(beginn + 1, end, row, playerId, newgrid)
    } else grid
  }

  def setTurnRest(index: IndexedSeq[(Int, Int)], playerId: Int, grid: Grid): Grid = {
    if (index.nonEmpty) {
      val newgrid = grid.set(index.head._1, index.head._2, playerId)
      setTurnRest(index.drop(1), playerId, newgrid)
    } else grid
  }

/*
  def getValidTurns(playerId: Int): List[Turn] = {
    if (playerId != 2 && playerId != 1) return Nil
    var retVal = new ListBuffer[Turn]
    for {
      row <- 0 until size
      col <- 0 until size
    } if (this.cell(row, col).value == playerId) {
      val f = Future(
        Future(lookup(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookdown(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookleft(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookright(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookupright(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookdownright(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookupleft(row, col, playerId, this)).map(option => option.map(retVal += _)),
        Future(lookdownleft(row, col, playerId, this)).map(option => option.map(retVal += _))
      )
      Await.result(f,Duration.Inf)
    }
    retVal.toList
  }

*/


def getValidTurns(playerId: Int): List[Turn] = if (playerId != 2 && playerId != 1) List() else
    getValidTurnsRecursive(rowcol(playerId), List(), playerId)


def getValidTurnsRecursive(index: IndexedSeq[(Int, Int)], list: List[Turn], playerId: Int): List[Turn] = {
  if (index.nonEmpty) {
    val map = List(
      lookup(index.head._1, index.head._2, playerId, this),
      lookdown(index.head._1, index.head._2, playerId, this),
      lookleft(index.head._1, index.head._2, playerId, this),
      lookright(index.head._1, index.head._2, playerId, this),
      lookupright(index.head._1, index.head._2, playerId, this),
      lookdownright(index.head._1, index.head._2, playerId, this),
      lookupleft(index.head._1, index.head._2, playerId, this),
      lookdownleft(index.head._1, index.head._2, playerId, this)
    ).flatten
    getValidTurnsRecursive(index.drop(1), list ::: map, playerId)
  } else list
  }

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == 0) return None
    val up = row - 1
    if (grid.cell(up, col).value != playerId && grid.cell(up, col).value != 0 && grid.cell(up, col).value != 3)
      for {
        up <- up until 0 by -1
      } if (grid.cell(up - 1, col).value == playerId) return None else if (grid.cell(up - 1, col).value == 0 ||
        grid.cell(up - 1, col).value == 3) return Some(Turn(row, col, up - 1, col, Direction.Up))
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1) return None
    val down = row + 1
    if (grid.cell(down, col).value != playerId && grid.cell(down, col).value != 0 && grid.cell(down, col).value != 3) {
      for {
        down <- down until grid.size - 1
      } if (grid.cell(down + 1, col).value == playerId) return None else if (grid.cell(down + 1, col).value == 0 ||
        grid.cell(down + 1, col).value == 3) return Some(Turn(row, col, down + 1, col, Direction
        .Down))
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == grid.size - 1) return None

    val right = col + 1

    if (grid.cell(row, right).value != playerId && grid.cell(row, right).value != 0 && grid.cell(row, right).value != 3) {
      for {
        right <- right until grid.size - 1
      } if (grid.cell(row, right + 1).value == playerId) return None else if (grid.cell(row, right + 1).value == 0 ||
        grid.cell(row, right + 1).value == 3) return Some(Turn(row, col, row, right + 1, Direction
        .Right))
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (col == 0) return None

    val left = col - 1

    if (grid.cell(row, left).value != playerId && grid.cell(row, left).value != 0 && grid.cell(row, left).value != 3) {
      for {
        left <- left until 0 by -1
      } if (grid.cell(row, left - 1).value == playerId) return None else if (grid.cell(row, left - 1).value == 0 ||
        grid.cell(row, left - 1).value == 3) return Some(Turn(row, col, row, left - 1, Direction
        .Left))
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
        right <- right until grid.size - 1
      } if (grid.cell(up - 1, right + 1).value == playerId) return None else if (grid.cell(up - 1, right + 1).value == 0
        || grid.cell(up - 1, right + 1).value == 3) return Some(Turn(row, col, up - 1, right + 1,
        Direction.UpRight))
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
      } if (grid.cell(up - 1, left - 1).value == playerId) return None else if (grid.cell(up - 1, left - 1).value == 0
        || grid.cell(up - 1, left - 1).value == 3) return Some(Turn(row, col, up - 1, left - 1,
        Direction.UpLeft))
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == grid.size - 1) return None

    val down = row + 1
    val right = col + 1

    if (grid.cell(down, right).value != playerId && grid.cell(down, right).value != 0 && grid.cell(down, right).value
      != 3) {
      for {
        down <- down until grid.size - 1
        right <- right until grid.size - 1
      } if (grid.cell(down + 1, right + 1).value == playerId) return None else if (grid.cell(down + 1, right + 1).value
        == 0 || grid.cell(down + 1, right + 1).value == 3) return Some(Turn(row, col, down + 1,
        right + 1, Direction
        .DownRight))
    }
    None
  }


  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if (row == grid.size - 1 || col == 0) return None

    val down = row + 1
    val left = col - 1

    if (grid.cell(down, left).value != playerId && grid.cell(down, left).value != 0 && grid.cell(down, left).value !=
      3) {
      for {
        down <- down until grid.size - 1
        left <- left until 0 by -1
      } if (grid.cell(down + 1, left - 1).value == playerId) return None else if (grid.cell(down + 1, left - 1).value
        == 0 || grid.cell(down + 1, left - 1).value == 3) return Some(Turn(row, col, down + 1, left
        - 1, Direction.DownLeft))
    }
    None
  }

  override def createNewGrid: GridInterface = (new GridCreator).createGrid(size)

  override def makeNextTurnBot(playerId: Int): GridInterface = (new Bot).makeTurn(this, playerId)

  def replaceXRecursive(rc: IndexedSeq[(Int, Int)], box: String): String = {
    if (box.contains("x")) {
      val save = box.replaceFirst("x", cell(rc.head._1, rc.head._2).toString)
      replaceXRecursive(rc.drop(1), save)
    } else box
  }

  override def toString: String = {
    val row, col = StringBuilder.newBuilder
    row.append("\n")
    for {i <- 0 until size} if (i == 0) row.append("  " + i.toString) else row.append(" " + i.toString)
    row.append("\n")

    val lineSeparator = (" +-" + ("--" * (size - 1))) + "+\n"
    val line = ("|" + (("x" + "|") * size)) + "\n"
    for {i <- 0 until size} col.append(i.toString + line + lineSeparator)

    val box = row + lineSeparator + col
    val rc = for {
      row <- 0 until size
      col <- 0 until size
    } yield (row, col)
    replaceXRecursive(rc, box)
  }
}
