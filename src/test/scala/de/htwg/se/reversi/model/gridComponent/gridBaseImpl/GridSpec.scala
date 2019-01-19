package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Reversi. A Grid" when {
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
      //val empty = tinyGrid.reset()
      "have the right values in the right places" in {
        smallGrid.cell(0, 0) should be(Cell(1))
        smallGrid.cell(0, 1) should be(Cell(2))
        smallGrid.cell(1, 0) should be(Cell(3))
        smallGrid.cell(1, 1) should be(Cell(4))
      }
      "values should be resetable" in {
        tinyGrid.reset(0,0).cell(0,0) should be(Cell(0))
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
    "In A Game" should {
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
      "special turn tests that are not covered yet" in {
        val lookdown = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(1), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(1), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(1), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0))
        )))
        lookdown.getValidTurns(1).size should be(0)

        val lookright = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(1), Cell(2)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(1), Cell(2), Cell(1)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0))
        )))
        lookdown.getValidTurns(1).size should be(0)

        val lookleft = Grid(new Matrix[Cell](Vector(
          Vector(Cell(2), Cell(1), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(1), Cell(2), Cell(1), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0))
        )))
        lookleft.getValidTurns(1).size should be(0)

      }
      "and no turns for anything else" in {
        freshGrid.getValidTurns(0).size should be(0)
        freshGrid.getValidTurns(3).size should be(0)
      }
      "these turns are highlighted for player 2" in {
        var highlighted = freshGrid.highlight(2)
        highlighted.cell(2,3) should be(Cell(3))
        highlighted.cell(3,2) should be(Cell(3))
        highlighted.cell(4,5) should be(Cell(3))
        highlighted.cell(5,4) should be(Cell(3))
      }
      "reset turns for player 1" in {
        var highlighted = freshGrid.highlight(2)
        var highlight2 = highlighted.highlight(1)
        highlight2.cell(2,3) should be(Cell(0))
        highlight2.cell(3,2) should be(Cell(0))
        highlight2.cell(4,5) should be(Cell(0))
        highlight2.cell(5,4) should be(Cell(0))
      }
      "test highlight downleft, upright, upleft, downright" in {
        var grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(1), Cell(2)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(0), Cell(0)))))
        grid.highlight(2).cell(2,0) should be(Cell(3))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(1), Cell(0)), Vector(Cell(1), Cell(1), Cell(0)), Vector(Cell(2), Cell(1), Cell(0)))))
        grid.highlight(2).cell(0,2) should be(Cell(3))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(0), Cell(0), Cell(0)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(1), Cell(2)))))
        grid.highlight(2).cell(0,0) should be(Cell(3))

        grid = Grid(new Matrix[Cell](Vector(Vector(Cell(2), Cell(1), Cell(0)), Vector(Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(0), Cell(0)))))
        grid.highlight(2).cell(2,2) should be(Cell(3))
      }
      "valid Turn should be true" in {
        freshGrid.checkChange(2,2,3)._1 should be(true)
      }
      "invalid Turn should be false" in {
        freshGrid.checkChange(2,2,4)._1 should be(false)
      }
    }
    "check all directions (up,down,left,right,upleft,upright,downleft,downright)" should {
      var grid = new Grid(4).createNewGrid
      var turns = grid.getValidTurns(1)
      print(turns)
      var gridsettodown = grid.checkChange(1,turns.head.toRow,turns.head.fromCol)
      var gridsettoright = grid.checkChange(1,turns(1).fromRow,turns(1).toCol)
      var gridsettoup = grid.checkChange(1,turns(2).toRow,turns(2).fromCol)
      var gridsettoleft = grid.checkChange(1,turns(3).fromRow,turns(3).toCol)
      "set direction down by Player1" in {
        var settodownmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(1), Cell(2), Cell(0)), Vector(Cell(0), Cell(1), Cell(1),Cell(0)), Vector(Cell(0), Cell(1),Cell(0),Cell(0)))))
        gridsettodown should be(true, settodownmanual)
      }
      "set direction right by Player1" in {
        var settorightmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(1), Cell(1), Cell(1)), Vector(Cell(0), Cell(2), Cell(1),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        gridsettoright should be (true, settorightmanual)
      }
      "set direction up by Player1" in {
        var settoupmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(1),Cell(0)), Vector(Cell(0), Cell(1), Cell(1), Cell(0)), Vector(Cell(0), Cell(2), Cell(1),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        gridsettoup should be(true, settoupmanual)
      }
      "set direction left by Player1" in {
        var settodownmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(1), Cell(2), Cell(0)), Vector(Cell(1), Cell(1), Cell(1),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        gridsettoleft should be(true, settodownmanual)
      }

      "set direction down left by Player2" in {
        var settodownleftmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(1), Cell(2), Cell(0)), Vector(Cell(0), Cell(2), Cell(1),Cell(0)), Vector(Cell(2), Cell(1),Cell(0),Cell(0)))))
        var p2turns = gridsettoleft._2.getValidTurns(2)
        gridsettodown._2.checkChange(2,p2turns(2).toRow,p2turns(2).toCol) should be(true, settodownleftmanual)
      }
      "set direction up right by Player2" in {
        var settouprightmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(2)), Vector(Cell(0), Cell(1), Cell(2), Cell(1)), Vector(Cell(0), Cell(2), Cell(1),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        var p2turns = gridsettoright._2.getValidTurns(2)
        gridsettoright._2.checkChange(2,p2turns(2).toRow,p2turns(2).toCol) should be(true, settouprightmanual)
      }
      "set direction up left by Player2" in {
        var gridbefore = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(1), Cell(1), Cell(1), Cell(0)), Vector(Cell(0), Cell(1), Cell(2),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        var settoupleftmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(2),Cell(0),Cell(0),Cell(0)), Vector(Cell(1), Cell(2), Cell(1), Cell(0)), Vector(Cell(0), Cell(1), Cell(2),Cell(0)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        var p2turns = gridbefore.getValidTurns(2)
        gridbefore.setTurn(p2turns(2),2) should be(settoupleftmanual)
      }
      "set direction down right by Player2" in {
        var gridbefore = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(2), Cell(1), Cell(0)), Vector(Cell(0), Cell(1), Cell(1),Cell(1)), Vector(Cell(0), Cell(0),Cell(0),Cell(0)))))
        var settodownrightmanual = Grid(new Matrix[Cell](Vector(Vector(Cell(0),Cell(0),Cell(0),Cell(0)), Vector(Cell(0), Cell(2), Cell(1), Cell(0)), Vector(Cell(0), Cell(1), Cell(2),Cell(1)), Vector(Cell(0), Cell(0),Cell(0),Cell(2)))))
        var p2turns = gridbefore.getValidTurns(2)
        gridbefore.setTurn(p2turns(2),2) should be(settodownrightmanual)
      }
    }
    "toString shows" in {
      var grid = new Grid(1).createNewGrid
      grid.toString should be("\n  0\n +-+\n0| |\n +-+\n")
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