package de.htwg.se.reversi

import com.google.inject.{Guice, Injector}
import de.htwg.se.reversi.aview.Tui
import de.htwg.se.reversi.aview.gui.SwingGui
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Reversi {
  val injector: Injector  = Guice.createInjector(new ReversiModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  //val gui = new SwingGui(controller)
  controller.createNewGrid()

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}