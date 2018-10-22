package de.htwg.se.reversi.model


case class Cell(value: Int) {
  def isSet: Boolean = value != 0
}

