package de.htwg.se.reversi.aview

import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.reversi.model.fileIoComponent._
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
    "enable bot on e" in {
      tui.processInputLine("e")
      controller.botState() should be(true)
    }
    "disable bot on d" in {
      tui.processInputLine("d")
      controller.botState() should be(false)
    }
    /*
    "set cells on input" in {
      val p1small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)),
        Vector(Cell(0), Cell(1), Cell(2), Cell(3)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(0)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      val p1smallafter = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(3), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)))))
      val p2small = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)))))
      val p2smallafter = Grid(new Matrix[Cell](Vector(
        Vector(Cell(0), Cell(3), Cell(0), Cell(0)),
        Vector(Cell(3), Cell(1), Cell(2), Cell(0)),
        Vector(Cell(0), Cell(2), Cell(1), Cell(3)),
        Vector(Cell(0), Cell(0), Cell(3), Cell(0)))))
      //tui.processInputLine("+")
      if (controller.activePlayer == 1) {
        controller.grid = p1small
        tui.processInputLine("02")
        controller.grid should be(p1small)
      } else {
        controller.grid = p2small
        tui.processInputLine("01")
        controller.grid should be(p2small)
      }
    }*/
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
      tui.processInputLine("f")
      var gridjson = new File(System.getProperty("user.dir") + "/grid.json")
      var playerjson = new File(System.getProperty("user.dir") + "/player.json")
      gridjson should exist
      playerjson should exist
    }
    "load a saved grid json" in {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      tui.processInputLine("l")
      controller.grid should be(normalGrid)
    }
    "save on f xml test" in {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      controller.fileIo = new fileIoXmlImpl.FileIO
      tui.processInputLine("f")
      var gridxml = new File(System.getProperty("user.dir") + "/grid.xml")
      var playerxml = new File(System.getProperty("user.dir") + "/player.xml")
      gridxml should exist
      playerxml should exist
    }
    "load a saved grid xml" in {
      val normalGrid = Grid(new Matrix[Cell](Vector(
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(0), Cell(2)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(0)),
        Vector(Cell(1), Cell(1), Cell(1), Cell(1),Cell(1), Cell(1), Cell(1), Cell(1)))))
      tui.processInputLine("l")
      controller.grid should be(normalGrid)
    }
  }

}
