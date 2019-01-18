package de.htwg.se.reversi.aview

import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}
import java.io.File

class TuiSpec extends WordSpec with Matchers{

  "A Go Tui" should {
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
    var controller = new Controller(new Grid(8))
    val tui = new Tui(controller)
    "be able to make en empty grid on input q" in {
      tui.processInputLine("q")
      controller.grid should be(new Grid(8))
    }
    "make a standart reversi field on n" in {

      tui.processInputLine("n")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1)
      } else {
        controller.grid should be(p2)
      }
    }
    "resize to size 1 on ." in {
      tui.processInputLine(".")
      controller.grid should be(new Grid(1))
    }
    "resize to size 4 on +" in {
      val p1small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      val p2small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)))))
      tui.processInputLine("+")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1small)
      } else {
        controller.grid should be(p2small)
      }

    }
    "resize back to normal on #" in {
      tui.processInputLine("#")
      if (controller.activePlayer == 1) {
        controller.grid should be(p1)
      } else {
        controller.grid should be(p2)
      }
    }
    "save on f" in {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      controller.grid = normalGrid
      print(controller.grid)
      controller.save()
      var gridjson = new File(System.getProperty("user.dir") + "/grid.json")
      var playerjson = new File(System.getProperty("user.dir") + "/player.json")
      gridjson should exist
      playerjson should exist
    }
    "load a saved grid" in {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      controller.load()
      controller.grid should be(normalGrid)

    }
  }

}
