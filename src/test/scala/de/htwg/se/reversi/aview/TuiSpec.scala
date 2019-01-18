package de.htwg.se.reversi.aview

import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{

  "A Go Tui" should {
    var controller = new Controller(new Grid(8))
    val tui = new Tui(controller)
    "be able to make en empty grid on input q" in {
      tui.processInputLine("q")
      controller.grid should be(new Grid(8))
    }
    "make a standart reversi field on n" in {
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
      tui.processInputLine("n")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1)
      } else {
        controller.grid should be(p2)
      }

    }
  }

}
