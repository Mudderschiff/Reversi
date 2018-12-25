package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl


import _root_.com.google.inject.name.Names
import _root_.com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}

import scala.swing.Publisher

class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  //val injector = Guice.createInjector(new ReversiModule)
  //val fileIo = injector.instance[FileIOInterface]

  override def cell(row: Int, col: Int) = grid.cell(row,col)

  override def set(row: Int, col: Int, value: Int) = grid.set(row,col,value)

  override def setTurnIndex(playerId: Int, index: Int): Unit = {
    //undoManager.doStep(new SetCommand(row,col,value,this))
    gameStatus = SET
    //publish(new CellChanged)
  }

  override def reset(row: Int, col: Int) = grid.reset(row,col)

  override def createNewGrid: Unit = {
    grid.size match {
      //case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      //case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      //case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    //publish(new CellChanged)
  }

  override def size: Int = ???

  override def evaluateGame(): Int = ??? //{
    //undoManager.doStep(new SolveCommand(this))
    //gameStatus = FINISH
    //publish(new CellChanged)
  //}

  override def highlight(playerId: Int): Unit = {
    grid = grid.highlight(playerId)
    //publish(new CellChanged)
  }

  override def setTurnRC(playerId: Int, row: Int, col: Int): Unit = {
    //undoManager.doStep(new SetCommand(row,col,value,this))
    gameStatus = SET
    //publish(new CellChanged)
  }
}
