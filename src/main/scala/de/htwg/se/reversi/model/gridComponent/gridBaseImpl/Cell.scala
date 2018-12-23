package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.CellInterface

case class Cell(value: Int) extends CellInterface {
  def isSet: Boolean = value != 0 & value != 3

  override def toString: String = value.toString.replace('0', ' ').replace('1', 'W').replace('2', 'B').replace('3', '*')
}
