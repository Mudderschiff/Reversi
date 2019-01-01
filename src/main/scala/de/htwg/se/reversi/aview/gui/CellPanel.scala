package de.htwg.se.reversi.aview.gui

import de.htwg.se.reversi.controller.controllerComponent.{CellChanged, ControllerInterface}
import de.htwg.se.reversi.model.gridComponent.CellInterface

import scala.swing._, scala.swing.event._, javax.swing.UIManager

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {
  val cellBlack = new Color(0, 0, 0)
  val cellWhite = new Color(255, 255, 255)
  val highlightedCellColor = new Color(192, 255, 192)
  val unhighlightedCellColor = new Color(255, 189, 189)

  def myCell: CellInterface = controller.cell(row, column)

  def cellText(row: Int, col: Int): String = ""
  val label:Label = new Label {
      text = cellText(row,column)
      font = new Font("Verdana", 1, 36)
    }

  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(80, 80)
    border = Swing.BeveledBorder(Swing.Lowered)
    listenTo(mouse.clicks)
    listenTo(mouse.moves)
    listenTo(controller)
    reactions += {
      case e: CellChanged =>
        label.text = cellText(row, column)
        repaint
      case MouseClicked(src, pt, mod, clicks, pops) =>
        controller.set(row,column,controller.getActivePlayer())
        if(controller.botstate()) controller.bot
        controller.finish
        repaint
      case MouseEntered(src,pt,mod) =>
        if (controller.cell(row,column).value == 3) {background = highlightedCellColor}
        else if (controller.cell(row,column).value == 0) {background = unhighlightedCellColor}
        repaint
      case MouseExited(src,pt,mod) =>
        setBackground(cell)
        repaint
    }
  }

  def redraw(): Unit = {
    contents.clear()
    label.text = cellText(row, column)
    setBackground(cell)
    contents += cell
    repaint
  }

  def setBackground(p: Panel): Unit =
    if (controller.cell(row,column).value == 1) {p.background = cellWhite}
    else if(controller.cell(row,column).value == 2) {p.background = cellBlack}
    else {p.background = UIManager.getColor(GridPanel)}
}