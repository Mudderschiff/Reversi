package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix, Turn}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Direction
import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Reversi. A Grid" when {
    "function validTurns" should {
      val freshGrid = new Grid(8).createNewGrid
      var turns = freshGrid.getValidTurns(1)
      "return for a fresh game these 4 possible turns for player1" in {
        turns.head should be(Turn(3, 3, 5, 3, Direction.Down))
        turns(1) should be(Turn(3, 3, 3, 5, Direction.Right))
        turns(2) should be(Turn(4, 4, 2, 4, Direction.Up))
        turns(3) should be(Turn(4, 4, 4, 2, Direction.Left))
      }
      "and these 4 turns for player2" in {
        turns = freshGrid.getValidTurns(2)
        turns.head should be(Turn(3, 4, 5, 4, Direction.Down))
        turns(1) should be(Turn(3, 4, 3, 2, Direction.Left))
        turns(2) should be(Turn(4, 3, 2, 3, Direction.Up))
        turns(3) should be(Turn(4, 3, 4, 5, Direction.Right))
      }
      "and no turns for anything else" in {
        freshGrid.getValidTurns(0).size should be(0)
        freshGrid.getValidTurns(3).size should be(0)
      }
      "test scenarios for DownLeft, UpRight, UpLeft, DownRight" in {
        var grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(1), Cell(2)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(0), Cell(0)))))
        grid.getValidTurns(2)(2) should be(Turn(0,2,2,0,Direction.DownLeft))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(1), Cell(0)), Vector(Cell(1), Cell(1), Cell(0)), Vector(Cell(2), Cell(1), Cell(0)))))
        grid.getValidTurns(2)(2) should be(Turn(2,0,0,2,Direction.UpRight))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(0), Cell(0)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(1), Cell(2)))))
        grid.getValidTurns(2)(2) should be(Turn(2,2,0,0,Direction.UpLeft))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(1), Cell(0)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(0), Cell(0)))))
        grid.getValidTurns(2)(2) should be(Turn(0,0,2,2,Direction.DownRight))
      }
    }
    "to be constructed" should {
      "be created with the length of its edges as size. Practically relevant are size 1, 3, and 8" in {
        val tinygrid = new Grid(1)
        val smallGrid = new Grid(3)
        val normalGrid = new Grid(8)
        val bigGrid = new Grid(16)
      }
      "for test purposes only created with a Matrix of Cells" in {
        val bigGrid = Grid(new Matrix(2, Cell(0)))
        val testGrid = Grid(Matrix[Cell](Vector(Vector(Cell(0), Cell(0)), Vector(Cell(0), Cell(0)))))
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(3)
      val normalGrid = new Grid(8)
      val bigGrid = new Grid(16)
      "give access to its Cells" in {
        tinygrid.cell(0, 0) should be(Cell(0))
        smallGrid.cell(0, 0) should be(Cell(0))
        smallGrid.cell(0, 1) should be(Cell(0))
        smallGrid.cell(1, 0) should be(Cell(0))
        smallGrid.cell(1, 1) should be(Cell(0))
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, 1)
        changedGrid.cell(0, 0) should be(Cell(1))
        smallGrid.cell(0, 0) should be(Cell(0))
      }
    }
    "prefilled with values 1 to n" should {
      val tinyGrid = Grid(new Matrix[Cell](Vector(Vector(Cell(1)))))
      val smallGrid = Grid(new Matrix[Cell](Vector(Vector(Cell(1), Cell(2)), Vector(Cell(3), Cell(4)))))
      "have the right values in the right places" in {
        smallGrid.cell(0, 0) should be(Cell(1))
        smallGrid.cell(0, 1) should be(Cell(2))
        smallGrid.cell(1, 0) should be(Cell(3))
        smallGrid.cell(1, 1) should be(Cell(4))
      }
      "have Houses with the right Cells" in {
        tinyGrid.row(0).cell(0) should be(Cell(1))
        tinyGrid.col(0).cell(0) should be(Cell(1))

        smallGrid.row(0).cell(0) should be(Cell(1))
        smallGrid.row(0).cell(1) should be(Cell(2))
        smallGrid.row(1).cell(0) should be(Cell(3))
        smallGrid.row(1).cell(1) should be(Cell(4))
        smallGrid.col(0).cell(0) should be(Cell(1))
        smallGrid.col(0).cell(1) should be(Cell(3))
        smallGrid.col(1).cell(0) should be(Cell(2))
        smallGrid.col(1).cell(1) should be(Cell(4))
      }
    }
  }
  "A Game is finished" when {
    "function finish" should {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      val tinyGrid = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(1)), Vector(Cell(1), Cell(2)))))
      "Current PLayer has no available turns" in {
        normalGrid.finish(2) should be(true)
      }
    }
    "evaluating Winner" should {
      val black = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(2)), Vector(Cell(0), Cell(1)))))
      val white = Grid(new Matrix[Cell](Vector(Vector(Cell(1), Cell(1)), Vector(Cell(0), Cell(2)))))
      val draw = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(2)), Vector(Cell(1), Cell(1)))))
      "Black schould win" in {
        black.evaluateGame() should be(2)
      }
      "White should win" in {
        white.evaluateGame() should be(1)
      }
      "Draw should occur" in {
        draw.evaluateGame() should be(0)
      }
    }
  }

}