package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.CellInterface

case class Cell(value: Int) extends CellInterface {
  def isSet: Boolean = value != 0

  override def toString: String = if(value == 0) " " else if(value == 1) "S" else if (value == 2) "W" else " "
}
