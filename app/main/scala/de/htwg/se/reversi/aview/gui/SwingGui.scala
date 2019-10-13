package de.htwg.wt.reversi.aview.gui

import de.htwg.wt.reversi.controller.controllerComponent._

import scala.swing._
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame {
  title = "HTWG Reversi"
  listenTo(controller)

  val scorelabel: Label = new Label {
    font = new Font("Verdana", 1, 36)
  }
  val statusline = new TextField(controller.statusText, 20)
  var cells: Array[Array[CellPanel]] = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)

  contents = new BorderPanel {
    add(scorepanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }
  size = new Dimension(800, 800)
  visible = true
  redraw()

  def resize(gridSize: Int): Unit = {
    cells = Array.ofDim[CellPanel](controller.gridSize, controller.gridSize)
    contents = new BorderPanel {
      add(scorepanel, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(statusline, BorderPanel.Position.South)
    }
    redraw()
  }

  def scorepanel: FlowPanel = new FlowPanel() {
    scorelabel.text = "B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString
    contents += scorelabel
    listenTo(controller)
    reactions += {
      case e: CellChanged =>
        scorelabel.text = "B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString
        repaint
    }
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Empty") {
        controller.createEmptyGrid()
      })
      contents += new MenuItem(Action("New") {
        controller.createNewGrid()
      })
      contents += new MenuItem(Action("Save") {
        controller.save()
      })
      contents += new MenuItem(Action("Load") {
        controller.load()
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
    }

    contents += new Menu("Bot") {
      mnemonic = Key.B
      contents += new MenuItem(Action("Enable") {
        controller.enableBot()
        if (controller.botState()) controller.bot()
      })
      contents += new MenuItem(Action("Disable") {
        controller.disableBot()
      })
    }

    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Size 1*1") {
        controller.resize(1)
      })
      contents += new MenuItem(Action("Size 4*4") {
        controller.resize(4)
      })
      contents += new MenuItem(Action("Size 8*8") {
        controller.resize(8)
      })
    }
  }

  reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event: CellChanged => redraw()
    case event: Finished => end()
    case event: BotStatus => redraw()
  }

  def gridPanel: FlowPanel = new FlowPanel() {
    contents += new GridPanel(cells.length, cells.length) {
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

  def redraw(): Unit = {
    for {
      row <- 0 until controller.gridSize
      column <- 0 until controller.gridSize
    } cells(row)(column).redraw()
    statusline.text = controller.statusText
  }

  def end(): Unit = {
    val end = StringBuilder.newBuilder
    end.append(controller.statusText)
    if (controller.evaluateGame() == 1) {
      end.append(" White Won:")
    } else if (controller.evaluateGame() == 2) {
      end.append(" Black Won:")
    } else {
      end.append(" Draw:")
    }
    end.append(" Final Score: B: " + controller.score()._1.toString + " | W: " + controller.score()._2.toString)
    statusline.text = end.toString()
  }
}