package de.htwg.se.reversi.model.fileIoComponent.fileIoMicroServiceImpl

import java.io._

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json._

import scala.io.Source.fromURL
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.{BasicResponseHandler}
import org.apache.http.HttpHeaders
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClients


class FileIO extends FileIOInterface {

  override def load: Option[GridInterface] = {
    var gridOption: Option[GridInterface] = None
    val source: String = fromURL("http://0.0.0.0:8070/load_grid").mkString
    val json: JsValue = Json.parse(source)
    val size = (json \ "grid" \ "size").get.toString.toInt
    val injector = Guice.createInjector(new ReversiModule)
    size match {
      case 1 => gridOption = Some(injector.instance[GridInterface](Names.named("tiny")))
      case 4 => gridOption = Some(injector.instance[GridInterface](Names.named("small")))
      case 8 => gridOption = Some(injector.instance[GridInterface](Names.named("normal")))
      case _ =>
    }
    gridOption match {
      case Some(grid) =>
        var _grid = grid
        for (index <- 0 until size * size) {
          val row = (json \\ "row") (index).as[Int]
          val col = (json \\ "col") (index).as[Int]
          val cell = (json \\ "cell") (index)
          val value = (cell \ "value").as[Int]
          _grid = _grid.set(row, col, value)
        }
        gridOption = Some(_grid)
      case None =>
    }
    gridOption
  }

  override def save(grid: GridInterface): Unit = {
    val json = gridToJson(grid)
    sendJson("http://0.0.0.0:8070/save_grid", json.toString())
  }

  def gridToJson(grid: GridInterface): JsObject = {
    Json.obj(
      "grid" -> Json.obj(
        "size" -> JsNumber(grid.size),
        "cells" -> Json.toJson(
          for {
            row <- 0 until grid.size
            col <- 0 until grid.size
          } yield {
            Json.obj(
              "row" -> row,
              "col" -> col,
              "cell" -> Json.toJson(grid.cell(row, col))
            )
          }
        )
      )
    )
  }

  override def loadPlayer: Int = {
    fromURL("http://0.0.0.0:8070/load_player").mkString.toInt
  }

  implicit val cellWrites: Writes[CellInterface] with Object {
    def writes(cell: CellInterface): JsObject
  } = new Writes[CellInterface] {
    def writes(cell: CellInterface): JsObject = Json.obj("value" -> cell.value)
  }

  override def savePlayer(activePlayer: Int): Unit = {
    sendJson("http://0.0.0.0:8070/save_player", activePlayer.toString)
  }

  def playerToJson(activePlayer: Int): JsObject = {
    Json.obj("activePlayer" -> JsNumber(activePlayer))
  }

  def sendJson(url: String, content: String): Unit = {

    val client = HttpClients.createDefault()
    val post = new HttpPost(url)
    post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json")
    val entity = new ByteArrayEntity(content.getBytes("UTF-8"))
    post.setEntity(entity)
    val result = client.execute(post, new BasicResponseHandler())
  }

}
