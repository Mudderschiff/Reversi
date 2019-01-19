package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import java.io.File

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "An Controller" when {

    "able to create empty and grid 1x1" in {
      var controller = new Controller(new Grid(1))
      controller.createEmptyGrid()
      controller.grid should be(new Grid(1))
      controller.createNewGrid()
      controller.grid should be(new Grid(1))
    }
    "able to create empty and new grid 4x4" in {
      var controller = new Controller(new Grid(4))
      controller.createEmptyGrid()
      controller.grid should be(new Grid(4))
      controller.createNewGrid()
      if(controller.getActivePlayer() == 2) controller.changePlayer()
      controller.grid should be(Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0))))))
    }
  }
}