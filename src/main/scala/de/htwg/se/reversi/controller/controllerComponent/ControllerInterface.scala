package de.htwg.se.reversi.controller.controllerComponent

import de.htwg.se.reversi.controller.controllerComponent.GameStatus.GameStatus
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{Cell, Grid}

import scala.collection.mutable.ListBuffer

trait ControllerInterface {

  //def gridSize:Int
  def createNewGrid: Unit
  //def resize(newSize:Int):Unit
  //def cell(row:Int, col:Int):CellInterface
  /*def set(row:Int, col:Int, value:Int):Unit
  def isSet(row:Int, col:Int):Boolean*/
  def gridToString:String
  def getValidTurns(playerId:Int):ListBuffer[(Int, Int)]
  def gameStatus:GameStatus
  def evaluateGame(grid:Grid):Int //PlayerId

}
