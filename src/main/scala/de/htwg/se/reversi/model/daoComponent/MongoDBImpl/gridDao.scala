package de.htwg.se.reversi.model.daoComponent.MongoDBImpl

import de.htwg.se.reversi.model.daoComponent.DAOInterface
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.model.Filters._

//import org.mongodb.scala.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class gridDao extends DAOInterface {
  // Create Codec. Codec is used to convert case class to BSON
  val codecRegistry = fromRegistries(fromProviders(classOf[Grid]), DEFAULT_CODEC_REGISTRY )

  //val mongoClient: MongoClient = MongoClient("mongodb://localhost:1234")
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("grids").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Grid] = database.getCollection("grid")
  val counter: MongoCollection[Document] = database.getCollection("counters")

  override def getGridById(id: Int): (Int, Int, String) = {
    Await.result(collection.find(equal("_id", id)).first().toFuture(),Duration.Inf).get()
  }

  override def getAllGrids: List[(Int, Int, String)] = {
    Await.result(collection.find().toFuture(),Duration.Inf).toList.map(grid => grid.get())
  }

  override def saveGrid(grid: String, player: Int): Unit = {
   // collection.insertOne("_id" -> "pdoc")
    Await.result(collection.insertOne(Grid(new ObjectId, player, grid)).toFuture(), Duration.Inf)
  }

  override def deleteGridById(id: Int): Boolean = {
    false
  }
}


case class Grid(_id: ObjectId,player: Int, grid: String) {
  def get(): (Int, Int, String) = (_id.toString.toInt,player,grid)
  //def getid(): Int = _id.toString.toInt
  //def getplayer(): Int = player
  //def getstring(): String = grid
}