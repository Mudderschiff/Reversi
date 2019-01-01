package de.htwg.se.reversi.aview

import com.typesafe.scalalogging.{LazyLogging, Logger}
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.controller.controllerComponent.{CellChanged, GridSizeChanged, Finished, BotStatus}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor with LazyLogging {
  listenTo(controller)
  def processInputLine(input: String):Unit = input match {
    case "q" => controller.createEmptyGrid
    case "n"=> controller.createNewGrid
    case "f" => controller.save
    case "l" => controller.load
    case "." => controller.resize(1)
    case "+" => controller.resize(4)
    case "#" => controller.resize(8)
    case "e" =>
      controller.enableBot()
      if(controller.botstate()) controller.bot
    case "d" => controller.disableBot()
    case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: Nil =>
        controller.set(row, column,controller.getActivePlayer())
        if(controller.botstate()) controller.bot
        controller.finish
      case _ =>
    }
  }

  reactions += {
    case event: GridSizeChanged => printTui()
    case event: CellChanged     => printTui()
    case event: Finished => printEnd()
    case event: BotStatus => printStatus()
  }

  def printStatus(): Unit = logger.info(GameStatus.message(controller.gameStatus))

  def printTui(): Unit = {
    logger.info(controller.gridToString)
    logger.info(GameStatus.message(controller.gameStatus))
    logger.info("B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString)
  }
  def printEnd(): Unit = {
    logger.info(GameStatus.message(controller.gameStatus))
    if(controller.evaluateGame() == 1) {
      logger.info("White Won")
    } else if (controller.evaluateGame() == 2) {
      logger.info("Black Won")
    } else {
      logger.info("Draw")
    }
    logger.info("Final Score: B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString)
  }
}
