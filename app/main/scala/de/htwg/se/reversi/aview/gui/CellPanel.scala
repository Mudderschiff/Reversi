package de.htwg.wt.reversi.aview.gui

import de.htwg.wt.reversi.controller.controllerComponent.{CellChanged, ControllerInterface}
import de.htwg.wt.reversi.model.gridComponent.CellInterface
import javax.swing.UIManager

import scala.swing._
import scala.swing.event._

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {
  val cellBlack = new Color(0, 0, 0)
  val cellWhite = new Color(255, 255, 255)
  val highlightedCellColor = new Color(192, 255, 192)
  val unhighlightedCellColor = new Color(255, 189, 189)
  val label: Label = new Label {
    text = cellText(row, column)
    font = new Font("Verdana", 1, 36)
  }
  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(65, 65)
    border = Swing.BeveledBorder(Swing.Lowered)
    listenTo(mouse.clicks)
    listenTo(mouse.moves)
    listenTo(controller)
    reactions += {
      case e: CellChanged =>
        label.text = cellText(row, column)
        repaint
      case MouseClicked(src, pt, mod, clicks, pops) =>
        controller.set(row, column, controller.getActivePlayer())
        if (controller.botState()) controller.bot()
        controller.finish()
        repaint
      case MouseEntered(src, pt, mod) =>
        if (controller.cell(row, column).value == 3) {
          background = highlightedCellColor
        }
        else if (controller.cell(row, column).value == 0) {
          background = unhighlightedCellColor
        }
        repaint
      case MouseExited(src, pt, mod) =>
        setBackground(cell)
        repaint
    }
  }

  def myCell: CellInterface = controller.cell(row, column)

  def redraw(): Unit = {
    contents.clear()
    label.text = cellText(row, column)
    setBackground(cell)
    contents += cell
    repaint
  }

  def cellText(row: Int, col: Int): String = ""

  def setBackground(p: Panel): Unit =
    if (controller.cell(row, column).value equals 1) {
      p.background = cellWhite
    }
    else if (controller.cell(row, column).value.equals(2)) {
      p.background = cellBlack
    }
    else {
      p.background = UIManager.getColor(GridPanel)
    }
}