package de.htwg.se.reversi.model.daoComponent.slickImpl

import de.htwg.se.reversi.model.daoComponent.DAOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface

import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class gridDao extends DAOInterface {
  private val grids = TableQuery[Grids]
  val db = Database.forConfig("h2mem1")
  Await.result(db.run(DBIO.seq(grids.schema.create)),Duration.Inf)

  override def getGridById(id: Int): (Int, Int, String) = {
    val query = db.run(grids.filter(_.id === id).result.headOption)
    Await.result(query, Duration.Inf).get
  }

  override def getAllGrids: List[(Int,Int, String)] = {
    val query = db.run(grids.result)
    Await.result(query,Duration.Inf).toList
  }

  override def saveGrid(grid: String, player: Int): Unit = {
    db.run(grids += (0,player,grid))
  }

  override def deleteGridById(id: Int): Boolean = {
    val query = db.run(grids.filter(_.id === id).delete) map { _ > 0}
    Await.result(query, Duration.Inf)
  }
}



case class Grids(tag: Tag) extends Table[(Int, Int, String)](tag, "Grids") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def player: Rep[Int] = column[Int]("Player")
  def grid: Rep[String] = column[String]("Grid")

  def * : ProvenShape[(Int, Int, String)] = (id,player,grid)
}
