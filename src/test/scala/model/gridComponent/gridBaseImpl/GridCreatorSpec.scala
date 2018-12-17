package model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, GridCreator}
import org.scalatest.{Matchers, WordSpec}

class GridCreatorSpec extends WordSpec with Matchers {
  "An GridCreator" when {
    val creator = GridCreator()
    "initialized with an even number" should {
      "be filled with zeros but in the middle of the field contain diagonal two 1 and two 2" in {
        val normalGrid = creator.createGrid(8)
        val tinyGrid = creator.createGrid(4)

        tinyGrid.cell(0,0) should be(Cell(0))
        tinyGrid.cell(0,1) should be(Cell(0))
        tinyGrid.cell(0,2) should be(Cell(0))
        tinyGrid.cell(0,3) should be(Cell(0))
        tinyGrid.cell(1,0) should be(Cell(0))
        tinyGrid.cell(1,1) should be(Cell(1))
        tinyGrid.cell(1,2) should be(Cell(2))
        tinyGrid.cell(1,3) should be(Cell(0))
        tinyGrid.cell(2,1) should be(Cell(2))
        tinyGrid.cell(2,2) should be(Cell(1))
        tinyGrid.cell(2,3) should be(Cell(0))
        tinyGrid.cell(2,0) should be(Cell(0))
        tinyGrid.cell(3,0) should be(Cell(0))
        tinyGrid.cell(3,1) should be(Cell(0))
        tinyGrid.cell(3,2) should be(Cell(0))
        tinyGrid.cell(3,3) should be(Cell(0))

      }
    }

    "initialized with an odd number or number below 4" should {

      "throw an Exception" in {

        an[Exception] should be thrownBy creator.createGrid(2)

        an[Exception] should be thrownBy creator.createGrid(7)

      }

    }
  }
}