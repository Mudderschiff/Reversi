package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.collection.mutable.ListBuffer

case class Grid(private val cells:Matrix[Cell]) extends GridInterface {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  val size:Int = cells.size
  var currentValidTurns:List[Turn] = Nil
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))
  def reset(row:Int, col:Int):Grid = copy(cells.replaceCell(row, col, Cell(0)))
  def row(row:Int):House = House(cells.rows(row))
  def col(col:Int):House = House(cells.rows.map(row=>row(col)))
  def finish(activePlayer: Int): Boolean = getValidTurns(activePlayer).isEmpty

  def highlight(playerId: Int): Grid = {
    var grid = this
    grid = grid.unhighlight()
    getValidTurns(playerId).foreach(turn => grid = grid.setHighlight(turn))
    grid
  }
  def unhighlight(): Grid = {
    var grid = this
    for {
      row <- 0 until size
      col <- 0 until size
    } if (grid.cell(row, col).value == 3) grid = grid.reset(row,col)
    grid
  }

  def setHighlight(turn:Turn):Grid = {
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

  def checkChange(gridnew: GridInterface): Boolean = {
    var bool = false
    var grid = this
    for {
      row <- 0 until size
      col <- 0 until size
    } if (grid.unhighlight().cell(row, col).value != gridnew.unhighlight().cell(row,col).value) bool = true
   bool
  }

  def setTurnRC(playerId: Int, row: Int, col: Int): Grid = {
    var grid = this
    getValidTurns(playerId).filter(turn => turn.toCol == col && turn.toRow == row).foreach(turn => grid = grid.setTurn(turn,playerId))
    currentValidTurns = Nil
    grid
  }

  def score(): (Int, Int) = {
    var (black, white) = (0,0)
    for {
      row <- 0 until size
      col <- 0 until size
    } if(cell(row,col).value.equals(2)) black +=1 else if (cell(row,col).value.equals(1)) white +=1
    (black, white)
  }

  def evaluateGame():Int = {
   val (black, white) = score()
    if (white > black) 1 else if (black > white) 2 else 0
  }


  def setTurn(turn:Turn, value:Int):Grid = {
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
    if(playerId != 2 && playerId != 1) {
      return Nil
    }

    if(currentValidTurns != Nil) {
      return currentValidTurns
    }

    var reval = new ListBuffer[Turn]

    for {
      row <- 0 until size
      col <- 0 until size
    } if(this.cell(row,col).value == playerId) {
      lookup(row, col, playerId, this).foreach(i => reval += i)
      lookdown(row, col, playerId, this).foreach(i => reval += i)
      lookleft(row, col, playerId, this).foreach(i => reval += i)
      lookright(row, col, playerId, this).foreach(i => reval += i)
      lookupright(row, col, playerId, this).foreach(i => reval += i)
      lookdownright(row, col, playerId, this).foreach(i => reval += i)
      lookupleft(row, col, playerId, this).foreach(i => reval += i)
      lookdownleft(row, col, playerId, this).foreach(i => reval += i)
    }
    reval.toList
  }

  private def lookup(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1) return None

    var up = row-1

    if(grid.cell(up,col).value != playerId && grid.cell(up,col).value != 0 && grid.cell(up,col).value != 3) {
      while (up > 0) {
        up -= 1
        if (grid.cell(up,col).value == playerId) return None
        if(grid.cell(up,col).value == 0 || grid.cell(up,col).value == 3) return Some(Turn(row,col,up,col,Direction.Up))
      }
      None
    }
    None
  }

  private def lookdown(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1) return None

    var down = row + 1

    if(grid.cell(down,col).value != playerId && grid.cell(down,col).value != 0 && grid.cell(down,col).value != 3) {
      while (down < grid.size - 1) {
        down += 1
        if (grid.cell(down,col).value == playerId) return None
        if(grid.cell(down,col).value == 0 || grid.cell(down,col).value == 3) return Some(Turn(row,col,down,col,Direction.Down))
      }
      None
    }
    None
  }

  private def lookright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(col == grid.size || col == grid.size - 1) return None

    var right = col + 1

    if(grid.cell(row,right).value != playerId && grid.cell(row,right).value != 0 && grid.cell(row,right).value != 3) {
      while (right < grid.size - 1) {
        right += 1
        if (grid.cell(row,right).value == playerId) return None
        if(grid.cell(row,right).value == 0 || grid.cell(row,right).value == 3) return Some(Turn(row,col,row,right,Direction.Right))
      }
      None
    }
    None
  }

  private def lookleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(col == 0 || col == 1) return None

    var left = col - 1

    if(grid.cell(row,left).value != playerId && grid.cell(row,left).value != 0 && grid.cell(row,left).value != 3) {
      while (left > 0) {
        left -= 1
        if (grid.cell(row,left).value == playerId) return None
        if(grid.cell(row,left).value == 0 || grid.cell(row,left).value == 3) return Some(Turn(row,col,row,left,Direction.Left))
      }
      None
    }
    None
  }

  private def lookupright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1 || col == grid.size || col == grid.size - 1) return None

    var up = row - 1
    var right = col + 1

    if(grid.cell(up,right).value != playerId && grid.cell(up,right).value != 0 && grid.cell(up,right).value != 3) {
      while (up > 0 && right < grid.size - 1) {
        up -= 1
        right += 1
        if (grid.cell(up,right).value == playerId) return None
        if(grid.cell(up,right).value == 0 || grid.cell(up,right).value == 3) return Some(Turn(row,col,up,right,Direction.UpRight))
      }
      None
    }
    None
  }

  private def lookupleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == 0 || row == 1 || col == 0 || col == 1) return None

    var up = row - 1
    var left = col - 1

    if(grid.cell(up,left).value != playerId && grid.cell(up,left).value != 0 && grid.cell(up,left).value != 3) {
      while (up > 0 && left > 0) {
        up -= 1
        left -= 1
        if (grid.cell(up,left).value == playerId) return None
        if(grid.cell(up,left).value == 0 || grid.cell(up,left).value == 3) return Some(Turn(row,col,up,left, Direction.UpLeft))
      }
      None
    }
    None
  }

  private def lookdownright(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1 || col == grid.size || col == grid.size - 1) return None

    var down = row + 1
    var right = col + 1

    if(grid.cell(down,right).value != playerId && grid.cell(down,right).value != 0 && grid.cell(down,right).value != 3) {
      while (down < grid.size - 1 && right < grid.size - 1) {
        down += 1
        right += 1
        if (grid.cell(down,right).value == playerId) return None
        if(grid.cell(down,right).value == 0 || grid.cell(down,right).value == 3) return Some(Turn(row,col,down,right, Direction.DownRight))
      }
      None
    }
    None
  }

  private def lookdownleft(row: Int, col: Int, playerId: Int, grid: Grid): Option[Turn] = {
    if(row == grid.size || row == grid.size - 1 || col == 0 || col == 1) return None

    var down = row + 1
    var left = col - 1

    if(grid.cell(down,left).value != playerId && grid.cell(down,left).value != 0 && grid.cell(down,left).value != 3) {
      while (down < grid.size - 1 && left > 0) {
        down += 1
        left -= 1
        if (grid.cell(down,left).value == playerId) return None
        if(grid.cell(down,left).value == 0 || grid.cell(down,left).value == 3) return Some(Turn(row,col,down,left,Direction.DownLeft))
      }
      None
    }
    None
  }
  
  override def makeNextTurnRandom(playerId: Int): Grid = (new ChooseTurn(this)).makeNextTurnRandom(playerId)
  override def makeNextTurnKI(playerId: Int): Grid = (new ChooseTurn(this)).makeNextTurnKI(playerId)
  override def createNewGrid: GridInterface = (new GridCreator).createGrid(size)

  override def toString: String = {
    val row, col = StringBuilder.newBuilder
    row.append("\n")
    for {i <- 0 until size} if (i == 0) row.append("  " + i.toString) else row.append(" " + i.toString)
    row.append("\n")

    val lineseparator = (" +-" + ("--" * (size - 1))) + "+\n"
    val line = ("|" + (("x" + "|") * size)) + "\n"
    for {i <- 0 until size } col.append(i.toString + line + lineseparator)

    var box = row + lineseparator  + col
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }
}
