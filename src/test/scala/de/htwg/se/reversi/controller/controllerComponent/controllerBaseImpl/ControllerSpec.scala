package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid
import java.io.File

import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.util.Observer

import scala.language.reflectiveCalls
import org.scalatest.{Matchers, WordSpec}
/*
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "empty" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid)
      "initialise with a starting player" in {
        controller.grid.cell(0, 0).isSet should be(false)
        controller.activePlayer should be(controller.getActivePlayer())
      }
      "Botstate is disabled" in {
        controller.botState() should be(false)
      }
      "IDLE gamestatus" in {
        controller.gameStatus should be(GameStatus.IDLE)
      }
    }
  }
}
*/