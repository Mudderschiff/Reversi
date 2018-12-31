package de.htwg.se.reversi.aview.gui

import de.htwg.se.reversi.controller.controllerComponent.{BotStatus, CandidatesChanged, CellChanged, ControllerInterface, Finished, GridSizeChanged}
import javax.swing.JPanel

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "HTWG Reversi"
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
/*
 def scorepanel = new FlowPanel {
    //logger.info("B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString)
    contents += new Label("Score:")
    val score = new TextComponent
    score.text = "B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString
    contents += score
  }
*/
  def gridPanel: GridPanel = new GridPanel(1,1) {
    contents += new GridPanel(cells.length, cells.length) {
      for {
        innerRow <- cells.indices
        innerColumn <- cells.indices
      } {
        val cellPanel = new CellPanel(innerRow, innerColumn, controller)
        cells(innerRow)(innerColumn) = cellPanel
        contents += cellPanel
        listenTo(cellPanel)
      }
    }
  }
  val statusline = new TextField(controller.statusText, 20)
  controller.finish

  contents = new BorderPanel {
    //add(scorepanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Empty") { controller.createEmptyGrid })
      contents += new MenuItem(Action("New") { controller.createNewGrid })
      contents += new MenuItem(Action("Save") { controller.save })
      contents += new MenuItem(Action("Load") { controller.load })
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }

    contents += new Menu("Bot") {
      mnemonic = Key.B
      contents += new MenuItem(Action("Enable") {
        controller.enableBot()
        if(controller.botstate()) controller.bot
      })
      contents += new MenuItem(Action("Disable") { controller.disableBot()})
    }

    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Size 1*1") { controller.resize(1) })
      contents += new MenuItem(Action("Size 4*4") { controller.resize(4) })
      contents += new MenuItem(Action("Size 8*8") { controller.resize(8) })

    }
  }

  visible = true
  redraw()

  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged     => resize(controller.gridSize)
    case event: Finished => end
    case event: BotStatus => redraw()
  }

  def resize(gridSize: Int): Unit = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      //add(scorepanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
    redraw()
  }
  def redraw(): Unit = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw
    statusline.text = controller.statusText
    repaint
  }

  def end(): Unit = {
    val end = StringBuilder.newBuilder
    end.append(controller.statusText)
      if(controller.evaluateGame() == 1) {
        end.append(" White Won")
      } else if (controller.evaluateGame() == 2) {
        end.append(" Black Won")
      } else {
        end.append(" Draw")
      }
    end.append(" Final Score: B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString)
    statusline.text = end.toString()
  }
}