package de.htwg.se.reversi.model.daoComponent.MongoDBImpl
import de.htwg.se.reversi.model.daoComponent.DAOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface

class gridDao extends DAOInterface {
  override def getGridOptionById(id: String): Option[GridInterface] = ???

  override def getAllGrids: List[GridInterface] = ???

  override def saveGrid(grid: GridInterface): Unit = ???

  override def deleteGrid(grid: GridInterface): Boolean = ???

  override def deleteGridById(id: String): Boolean = ???
}
