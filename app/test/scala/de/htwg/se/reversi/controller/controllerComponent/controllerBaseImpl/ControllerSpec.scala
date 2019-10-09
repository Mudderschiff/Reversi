package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "An Controller" when {
    var controller = new Controller(new Grid(1))

    "able to create empty grid 1x1 with a gridsize of 1" in {
      controller.createEmptyGrid()
      controller.grid should be(new Grid(1))
      controller.gridSize should be(1)
    }
    "create a new grid 1x1" in {
      controller.createNewGrid()
      controller.grid should be(new Grid(1))
      controller.statusText should be("A new game was created")
    }
    "set a cell" in {
      controller.cell(0, 0)
      controller.grid should be(new Grid(1))
    }
    "able to create empty and new grid 4x4" in {
      var controller = new Controller(new Grid(4))
      controller.createEmptyGrid()
      controller.grid should be(new Grid(4))
      controller.createNewGrid()
      if (controller.getActivePlayer() == 2) {
        controller.grid should be(Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
          Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
          Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
          Vector(Cell(0), Cell(0), Cell(3), Cell(0))))))
      } else {
        controller.grid should be(Grid(new Matrix[Cell](Vector(
          Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
          Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
          Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
          Vector(Cell(0), Cell(3), Cell(0), Cell(0))))))
      }

    }
  }
}