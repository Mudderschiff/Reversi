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

  def getValidCells(playerId: Int): ListBuffer[(Int, Int)] = {
    val obtainedCells = List()
    var reval = new ListBuffer[(Int, Int)];

    for (cell <- obtainedCells) {
      //Todo: Richtige parameter übergeben aber v.a. vorher durch das Grid iterieren
      lookup() match {
        case Some(value) => reval += value
        case None => _
      }
      lookdown() match {
        case Some(value) => reval += value
        case None => _
      }

    }
    reval
  }

  def lookup(row: Int, col: Int, playerId: Int): Option[(Int, Int)] = {
    if(row == 0 || row == 1) None

    var up = row-1

    if(grid.cell(up,col).value != playerId && grid.cell(up,col).value != 0) {
      while (up > 1) {
        up = up-1
        if(grid.cell(up,col).value == 0) Some((up,col))
      }
      None
    }
    None
  }

  def lookdown(row: Int, col: Int, playerId: Int): Option[(Int, Int)] = {
    if(row == grid.size || row == grid.size - 1) None

    var down = row + 1

    if(grid.cell(down,col).value != playerId && grid.cell(down,col).value != 0) {
      while (down < grid.size -1) {
        down = down + 1
        if(grid.cell(down,col).value == 0) Some((down,col))
      }
      None
    }
    None
  }
}
