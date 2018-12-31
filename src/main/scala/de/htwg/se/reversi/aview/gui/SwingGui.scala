package de.htwg.se.reversi.aview.gui

import de.htwg.se.reversi.controller.controllerComponent.{ CandidatesChanged, CellChanged, ControllerInterface, GridSizeChanged }

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "HTWG Reversi"
  var cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)

  def gridPanel = new GridPanel(1,1) {
    //border = LineBorder(java.awt.Color.BLACK, 2)
    //background = java.awt.Color.BLACK
    contents += new GridPanel(controller.gridSize, controller.gridSize) {
      for {
        innerRow <- 0 until controller.gridSize
        innerColumn <- 0 until controller.gridSize
      } {
        val cellPanel = new CellPanel(innerRow, innerColumn, controller)
        cells(innerRow)(innerColumn) = cellPanel
        contents += cellPanel
        listenTo(cellPanel)
      }
    }
  }
  val statusline = new TextField(controller.statusText, 20)

  contents = new BorderPanel {
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
    contents += new Menu("Highlight") {
      mnemonic = Key.H
      for { index <- 0 to controller.gridSize } {
        contents += new MenuItem(Action(index.toString) { controller.highlight(index) })
      }
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Size 1*1") { controller.resize(1) })
      contents += new MenuItem(Action("Size 4*4") { controller.resize(4) })
      contents += new MenuItem(Action("Size 9*9") { controller.resize(9) })

    }
  }

  visible = true
  redraw

  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged => redraw
    case event: CandidatesChanged => redraw
  }

  def resize(gridSize: Int) = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
  }
  def redraw = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw
    statusline.text = controller.statusText
    repaint
  }
}