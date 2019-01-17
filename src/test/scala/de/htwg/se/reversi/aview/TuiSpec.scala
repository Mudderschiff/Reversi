package de.htwg.se.reversi.aview

import de.htwg.se.reversi.Reversi.injector
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid}
import de.htwg.se.reversi.model.playerComponent.Player
//import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

//@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers{

  "A Go Tui" should {
    val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
    val tui = new Tui(controller)
    "create and empty Sudoku on input 'q'" in {
      tui.processInputLine("q") should be()
      //controller.asGame should be(new Grid(9), (Player("Player 1", Cell(CellStatus.BLACK)),
       // Player("Player 2", Cell(CellStatus.WHITE))))
    }
    "set cell" in {

    }
  }

}
