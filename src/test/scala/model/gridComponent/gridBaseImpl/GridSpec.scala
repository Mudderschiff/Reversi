package model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Matrix}
import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Reversi. A Grid" when {
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

}
