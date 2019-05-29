package de.htwg.se.reversi.model.daoComponent
import de.htwg.se.reversi.model.gridComponent.GridInterface

trait DAOInterface {
  def getGridOptionById(id: String): Option[GridInterface]
  def getGridById(id: String): GridInterface = getGridOptionById(id).get
  def getAllGrids: List[GridInterface]

  def saveGrid(grid: GridInterface): Unit

  def deleteGrid(grid: GridInterface): Boolean
  def deleteGridById(id: String): Boolean
}
