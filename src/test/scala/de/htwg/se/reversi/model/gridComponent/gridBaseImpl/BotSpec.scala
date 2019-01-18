package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import com.google.common.collect.Table
import org.scalatest.{Matchers, WordSpec}
import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.se.reversi.model.playerComponent.Player

class BotSpec extends WordSpec with Matchers {
  "The Reversi Bot" when {
    "in a running game" should {

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
        afterbot should not be(runninggame)

      }
    }
  }
}
