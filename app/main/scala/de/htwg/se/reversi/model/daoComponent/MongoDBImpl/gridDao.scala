package de.htwg.se.reversi.model.daoComponent.MongoDBImpl

import com.mongodb.BasicDBObject
import de.htwg.se.reversi.model.daoComponent.DAOInterface
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.model.Filters._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class gridDao extends DAOInterface {
  // Create Codec. Codec is used to convert case class to BSON
  val codecRegistry = fromRegistries(fromProviders(classOf[Grid]), DEFAULT_CODEC_REGISTRY )

  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("grids").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Grid] = database.getCollection("grid")
  val counter: MongoCollection[Document] = database.getCollection("counters")

  Await.result(counter.insertOne(Document("_id" -> "userid","seq" -> 0)).toFuture(),Duration.Inf)

  override def getGridById(id: Int): (Int, Int, String) = {
    Await.result(collection.find(equal("_id", id)).first().toFuture(),Duration.Inf).get()
  }

  override def getAllGrids: List[(Int, Int, String)] = {
    Await.result(collection.find().toFuture(),Duration.Inf).toList.map(grid => grid.get())
  }

  override def saveGrid(grid: String, player: Int): Unit = {
    Await.result(collection.insertOne(Grid(getNextSequence("userid"), player, grid)).toFuture(), Duration.Inf)
  }

  override def deleteGridById(id: Int): Boolean = {
    Await.result(collection.deleteOne(equal("_id", id)).toFuture(),Duration.Inf).wasAcknowledged()
  }
  def getNextSequence(userid: String) = {
    val searchQuery = new BasicDBObject("_id", userid)
    val increase = new BasicDBObject("seq", 1)
    val updateQuery = new BasicDBObject("$inc", increase)
    Await.result(counter.findOneAndUpdate(searchQuery,updateQuery).toFuture(),Duration.Inf).last._2.asInt32().getValue
  }
}




case class Grid(_id: Int,player: Int, grid: String) {
  def get(): (Int, Int, String) = (_id ,player,grid)
}

