package de.htwg.se.reversi.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.xml.{Elem, NodeSeq, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load: Option[GridInterface] = {
    var gridOption: Option[GridInterface] = None
    val file = scala.xml.XML.loadFile("grid.xml")
    val sizeAttr = file \\ "grid" \ "@size"
    val size = sizeAttr.text.toInt
    val injector = Guice.createInjector(new ReversiModule)
    size match {
      case 1 => gridOption = Some(injector.instance[GridInterface](Names.named("tiny")))
      case 4 => gridOption = Some(injector.instance[GridInterface](Names.named("small")))
      case 8 => gridOption = Some(injector.instance[GridInterface](Names.named("normal")))
      case _ =>
    }
    val cellNodes= file \\ "cell"
    gridOption match {
      case Some(grid)=>
        var _grid = grid
        for (cell <- cellNodes) {
          val row: Int = (cell \ "@row").text.toInt
          val col: Int = (cell \ "@col").text.toInt
          val value: Int = cell.text.trim.toInt
          _grid = _grid.set(row, col, value)
        }
        gridOption = Some(_grid)
      case None =>
    }
    gridOption
  }

  def save(grid:GridInterface):Unit = saveString(grid)


  override def loadPlayer: Int = {
    val file = scala.xml.XML.loadFile("player.xml")
    (file \\ "player").text.trim.toInt
  }

  override def savePlayer(activePlayer: Int): Unit = scala.xml.XML.save("player.xml", playerToXML(activePlayer))

  def playerToXML(activePlayer: Int): Elem = <player>{activePlayer}</player>

  def saveXML(grid:GridInterface):Unit = scala.xml.XML.save("grid.xml", gridToXml(grid))

  def saveString(grid:GridInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.xml" ))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gridToXml(grid))
    pw.write(xml)
    pw.close()
  }
  def gridToXml(grid:GridInterface): Elem = {
    <grid size ={grid.size.toString}>
      {
      for {
        row <- 0 until grid.size
        col <- 0 until grid.size
      } yield cellToXml(grid, row, col)
      }
    </grid>
  }

  def cellToXml(grid:GridInterface, row:Int, col:Int): Elem = {
    <cell row ={row.toString} col={col.toString}>
      {grid.cell(row,col).value}
    </cell>
  }

}
