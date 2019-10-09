package de.htwg.se.reversi.model.gridComponent.gridMockImpl

import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Mock Grid" when {
    val grid = new Grid(2)
    "created properly but empty" should {
      "always be size of 1" in {
        grid.size should be(1)
      }
      "return a EmptyCell" in {
        grid.cell(0, 0) should be(EmptyCell)
      }
      "EmptyCell isn't set" in {
        grid.cell(0, 0).isSet should be(false)
      }
      "return no Turns" in {
        grid.getValidTurns(1) should be(List())
      }
      "Begins in a tie" in {
        grid.evaluateGame() should be(0)
      }

      "is not finished" in {
        grid.finish(1) should be(false)
      }

      "scoreboard: 0-0" in {
        grid.score() should be((0, 0))
      }

      "nothing ever changes" in {
        grid.checkChange(1, 0, 0) should be(false, grid)
      }

    }
  }
}
