package de.htwg.se.reversi.model.gridComponent.gridMockImpl

import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Mock Grid" when {
    val grid = new Grid(2)
    "created not properly but empty" should {
      "always be size of 1 anyways" in {
        grid.size should be (1)
      }

      "return a EmptyCell" in {
        grid.cell(0,0) should be(EmptyCell)
      }

      "set does nothing" in {
        grid.set(0,0,1) should be(grid)
      }

      "return no Turns" in {
        grid.getValidTurns(1) should be(List())
      }

      "creates an empty grid" in {
        grid.createNewGrid should be(grid)
      }

      "game is eval" in {
        grid.evaluateGame() should be(0)
      }

      "highlights nothing" in {
        grid.highlight(1) should be(grid)
      }

      "bot does nothing" in {
        grid.makeNextTurnBot(1) should be(grid)
      }

      "is never finished" in {
        grid.finish(1) should be(false)
      }

      "score is always 0" in {
        grid.score() should be((0,0))
      }

      "nothing ever changes" in {
        grid.checkChange(1,0,0) should be(false, grid)
      }

    }
  }
}
