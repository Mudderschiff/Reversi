package de.htwg.se.reversi.aview.gui

import java.awt.Color
import java.awt.geom.{Ellipse2D, Rectangle2D}

import scala.swing._
import scala.swing.event._
import de.htwg.se.reversi.controller.controllerComponent.{BotStatus, CellChanged, ControllerInterface, Finished}
import javax.swing.UIManager

class CellPanel(row: Int, column: Int, controller: ControllerInterface) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)
  val cellBlack = new Color(0, 0, 0)
  val cellWhite = new Color(255, 255, 255)
  val highlightedCellColor = new Color(192, 255, 192)
  val unhighlightedCellColor = new Color(255, 189, 189)
  //val black = new B

  def myCell = controller.cell(row, column)

  //def cellText(row: Int, col: Int) = if(controller.cell(row,column).value == 3)  "" else  "" + controller.cell(row,column)
  def cellText(row: Int, col: Int) = ""
  val label =
    new Label {
      text = cellText(row,column)
      font = new Font("Verdana", 1, 36)
      background = if (controller.cell(row,column).value == 1) cellWhite else cellBlack
      //background = cellColor
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(80, 80)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(mouse.moves)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        if (!controller.cell(row,column).isSet) {background = UIManager.getColor(Button)}
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        controller.set(row,column,controller.getActivePlayer())
        if(controller.botstate()) controller.bot
        controller.finish
        repaint
      }
      case MouseEntered(src,pt,mod) => {
        if (controller.cell(row,column).value == 3) {background = highlightedCellColor }
        else if (controller.cell(row,column).value == 0) {background = unhighlightedCellColor }
        repaint
      }
      case MouseExited(src,pt,mod) => {
        //setBackground(UIManager.getColor(Button.background))
        if (!controller.cell(row,column).isSet) {background = UIManager.getColor(Button)}
        repaint
      }
    }
  }

  def redraw = {
    contents.clear()
    label.text = cellText(row, column)
    setBackground(cell)
    contents += cell
    repaint
  }

  def setBackground(p: Panel) = if (controller.cell(row,column).value == 1) {
    p.background = cellWhite
  } else if (controller.cell(row,column).value == 2) p.background = cellBlack
}