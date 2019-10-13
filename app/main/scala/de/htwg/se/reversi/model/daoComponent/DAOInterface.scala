package de.htwg.wt.reversi.model.daoComponent

trait DAOInterface {
  def getGridById(id: Int): (Int, Int, String)
  def getAllGrids: List[(Int,Int, String)]

  def saveGrid(grid: String, player: Int): Unit

  def deleteGridById(id: Int): Boolean
}
