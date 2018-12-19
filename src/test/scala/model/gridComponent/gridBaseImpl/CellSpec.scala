package model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Cell
import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = Cell(0)
      "have value 0" in {
        emptyCell.value should be(0)
      }
      "not be set" in {
        emptyCell.isSet should be(false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = Cell(5)
      "return that value" in {
        nonEmptyCell.value should be(5)
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
    }
    "toString 0" should {
      val stringCell0 = Cell(0)
      "return toString" in {
        stringCell0.toString should be(" ")
      }
    }
    "toString 1" should {
      val stringCell1 = Cell(1)
      "return toString" in {
        stringCell1.toString should be("W")
      }
    }
    "toString 2" should {
      val stringCell2 = Cell(2)
      "return toString" in {
        stringCell2.toString should be("B")
      }
    }
  }

}
