package de.htwg.se.reversi

import de.htwg.se.reversi.model.playerComponent.Player
import de.htwg.se.reversi.aview
import de.htwg.se.reversi.aview.Tui
/*
object Reversi {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
*/

//import de.htwg.se.sudoku.model.{Grid, GridCreator, Player, Solver}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.GridCreator
import de.htwg.se.reversi.model.playerComponent.Player
import de.htwg.se.reversi.aview.Tui

import scala.io.StdIn.readLine

object Reversi {
  var grid = new Grid(8)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Grid : " + grid.toString)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "q")
  }
}