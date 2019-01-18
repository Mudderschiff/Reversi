package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import java.io.File

import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    var controller = new Controller(new Grid(8))
    "creating empty grid" should {
      "be empty" in {
        controller.createEmptyGrid()
        controller.grid should be(new Grid(8))
      }
      "initialise with a starting player" in {
        controller.activePlayer should be(controller.getActivePlayer())
      }
      "have no Bot active" in {
        controller.botState() should be(false)
      }
      "have a IDLE game status" in {
        controller.gameStatus should be(GameStatus.IDLE)
      }
    }
    "creating new Grid" should {
      val p1 = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(3), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(1),Cell(2), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(2),Cell(1), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(3),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)))))
      val p2 = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(3),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(1),Cell(2), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(2),Cell(1), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(3), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)),
        Vector(Cell(0), Cell(0), Cell(0), Cell(0),Cell(0), Cell(0), Cell(0), Cell(0)))))
      "set gamepieces correctly" in {
        controller.createNewGrid()
        if (controller.activePlayer == 1) {
          controller.grid should be(p1)
        } else {
          controller.grid should be(p2)
        }
      }
    }
  }
}
