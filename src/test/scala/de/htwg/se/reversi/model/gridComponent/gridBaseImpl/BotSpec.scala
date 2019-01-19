package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import org.scalatest.{Matchers, WordSpec}

class BotSpec extends WordSpec with Matchers {
  "The Reversi Bot" when {

    "in a running game with only one possible turn with the best value" should {

      val runninggame = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      )))
      "choose a random turn out of the best value category and return a new grid" in {
        val afterbot = runninggame.makeNextTurnBot(2)
        val runninggameafter = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        )))
        afterbot should be(runninggameafter)
      }
    }

    "in a running game with only one possible turn with the second best value" should {

      val runninggame = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      )))
      "choose a random turn out of the best value category and return a new grid" in {
        val afterbot = runninggame.makeNextTurnBot(2)
        val runninggameafter = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        )))
        afterbot should be(runninggameafter)
      }
    }

    "in a running game with only one possible turn with the neutral or third best value" should {

      val runninggame = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(1), Cell(2), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      )))
      "choose a random turn out of the best value category and return a new grid" in {
        val afterbot = runninggame.makeNextTurnBot(2)
        val runninggameafter = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(2), Cell(2), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        )))
        afterbot should be(runninggameafter)
      }
    }

    "in a running game with only one possible turn with the second worst value" should {
      val runninggame = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      )))
      "choose a random turn out of the best value category and return a new grid" in {
        val afterbot = runninggame.makeNextTurnBot(2)
        val runninggameafter = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(2), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        )))
        afterbot should be(runninggameafter)
      }
    }

    "in a running game with only one possible turn with the worst value" should {

      val runninggame = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
      )))
      "choose a random turn out of the best value category and return a new grid" in {
        val afterbot = runninggame.makeNextTurnBot(2)
        val runninggameafter = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(2), Cell(2), Cell(2), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0), Cell(0)),
        )))
        afterbot should be(runninggameafter)
      }
    }

    "a empty grid" should {
      "do nothing" in {
        val grid = new Grid(8)
        val after = grid.makeNextTurnBot(1)
        grid should be(after)
      }
    }

    "a small grid" should {
      "not do anything for region 3" in {
        val small = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(2), Cell(1), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0))
        )))
        val after = Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(2), Cell(2), Cell(2), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0)),
          Vector(Cell(0), Cell(0), Cell(0), Cell(0))
        )))

        small.makeNextTurnBot(2) should be(after)
      }
    }
  }
}