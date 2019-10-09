package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class House(private val cells: Vector[Cell]) {
  def cell(index: Int): Cell = cells(index)
}
