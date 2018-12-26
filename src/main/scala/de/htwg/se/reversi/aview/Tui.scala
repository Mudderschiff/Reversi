package de.htwg.se.reversi.aview

import com.typesafe.scalalogging.{LazyLogging, Logger}
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.controller.controllerComponent.{CandidatesChanged, CellChanged, GridSizeChanged}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.reversi.model.playerComponent.Player

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor with LazyLogging{
  val player1 = new Player(1)
  val player2 = new Player(2)
  var activePlayer = player1.playerId
  def changePlayer(): Unit = if(activePlayer == 1)  activePlayer = player2.playerId else if (activePlayer == 2) activePlayer = player1.playerId
  listenTo(controller)

  def processInputLine(input: String):Unit = input match {
    case "q" => controller.createEmptyGrid
    case "n"=> controller.createNewGrid
    //case "f" => controller.save
    //case "l" => controller.load
    case "." => controller.resize(1)
    case "+" => controller.resize(4)
    case "#" => controller.resize(8)
    //case "h1" => grid.highlight(1)
    //case "h2" => grid.highlight(2)
    case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: Nil => {
        controller.set(row, column,activePlayer)
        changePlayer()
      }
      case _ =>
    }
  }

  reactions += {
    case event: GridSizeChanged => printTui
    case event: CellChanged     => printTui
    //case event: CandidatesChanged => printCandidates
  }

  def printTui: Unit = {
    logger.info(controller.gridToString)
    logger.info(GameStatus.message(controller.gameStatus))
  }
/*
  def printCandidates: Unit = {
    logger.info("Candidates: ")
    for (row <- 0 until size; col <- 0 until size) {
      if (controller.isShowCandidates(row, col)) println("("+row+","+col+"):"+controller.available(row, col).toList.sorted)
    }
  }*/
}
