package de.htwg.wt.reversi.model.gridComponent.gridBaseImpl

case class House(private val cells: Vector[Cell]) {
  def cell(index: Int): Cell = cells(index)
}
