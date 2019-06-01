package de.htwg.se.reversi.model.daoComponent.slickImpl

import de.htwg.se.reversi.model.daoComponent.DAOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

object gridDao extends DAOInterface {
  val db = Database.forConfig("h2mem1")
  try {
    //Await.result(resultFuture, Duration.Inf)
    //lines.foreach(Predef.println _)
  } finally db.close


  override def getGridOptionById(id: String): Option[GridInterface] = ???

  override def getAllGrids: List[GridInterface] = ???

  override def saveGrid(grid: GridInterface): Unit = ???

  override def deleteGrid(grid: GridInterface): Boolean = ???

  override def deleteGridById(id: String): Boolean = ???
}
