package de.htwg.se.reversi.controller.controllerComponent.controllerMockImpl

import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.model.gridComponent.gridMockImpl.Grid
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Mock Controller" when {
    val controller = new Controller(new Grid(1))
    "created properly" should {
      "have gridsize 1" in {
        controller.gridSize should be(1)
      }

      "does nothing on create empty grid" in {
        controller.createEmptyGrid() should be(())
      }

      "does nothing on create new grid" in {
        controller.createNewGrid() should be(())
      }

      "does nothing on save" in {
        controller.save() should be(())
      }

      "does nothing on load" in {
        controller.load() should be(())
      }

      "does nothing on resize" in {
        controller.resize(1) should be(())
      }

      "does nothing on set" in {
        controller.set(0, 0, 1) should be(())
      }

      "returns 0,0" in {
        controller.score() should be((0, 0))
      }

      "returns IDLE" in {
        controller.gameStatus should be(GameStatus.IDLE)
      }

      "return grid as string" in {
        controller.gridToString should be(controller.grid.toString)
      }

      "return active player" in {
        controller.getActivePlayer() should be(controller.getActivePlayer())
      }

      "does nothing on finish" in {
        controller.finish() should be(())
      }

      "evaluates 0" in {
        controller.evaluateGame() should be(0)
      }

      "botstatus" in {
        controller.botState() should be(false)
      }

      "does nothing on enableBot" in {
        controller.enableBot() should be(())
      }

      "does nothing on disableBot" in {
        controller.disableBot() should be(())
      }

      "does nothing on bot" in {
        controller.bot() should be(())
      }

      "gets statusText" in {
        controller.statusText should be(controller.statusText)
      }

      "get a cell" in {
        controller.cell(0, 0).value should be(0)
      }

      "does nothing on undo" in {
        controller.undo should be(())
      }

      "does nothing on redo" in {
        controller.redo should be(())
      }
    }
  }
}