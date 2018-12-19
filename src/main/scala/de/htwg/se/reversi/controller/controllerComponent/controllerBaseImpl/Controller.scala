package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, GridCreator}
import scala.collection.mutable.ListBuffer

class Controller(var grid: GridInterface) extends ControllerInterface{

  val gridcreator = new GridCreator

  def createNewGrid: Unit = {
    gridcreator.createGrid(8) match {
      case Right(x) => grid = x
      case Left(x) => //Todo
    }
  }

}
