package de.htwg.se.reversi.model.daoComponent
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.concurrent.Future

trait DAOInterface {
  def getGridById(id: Int): (Int, Int, String)
  def getAllGrids: List[(Int,Int, String)]

  def saveGrid(grid: String, player: Int): Unit

  def deleteGridById(id: Int): Boolean
}
