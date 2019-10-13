package de.htwg.wt.reversi

import com.google.inject.{Guice, Injector}
import de.htwg.wt.reversi.aview.Tui
import de.htwg.wt.reversi.aview.gui.SwingGui
import de.htwg.wt.reversi.aview.WebServer
import de.htwg.wt.reversi.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.wt.reversi.controller.controllerComponent.ControllerInterface
import de.htwg.wt.reversi.model.gridComponent.gridBaseImpl.Grid

import scala.io.StdIn.readLine

object Reversi {
  val injector: Injector = Guice.createInjector(new ReversiModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  //val server = new WebServer(controller)
  controller.createNewGrid()


  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
    //server.unbind
  }
}