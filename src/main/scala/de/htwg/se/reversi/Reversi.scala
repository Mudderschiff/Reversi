package de.htwg.se.reversi

import com.google.inject.Guice
import de.htwg.se.reversi.aview.Tui
//import de.htwg.se.reversi.aview.gui.SwingGui
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Reversi {
  val injector = Guice.createInjector(new ReversiModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  //var grid = new Grid(8)
  val tui = new Tui(controller)
  controller.createNewGrid

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      //println("Grid : " + grid.toString)
      input = readLine()
      //grid = tui.processInputLine(input, grid)
      tui.processInputLine(input)
    } while (input != "q")
  }
}*/