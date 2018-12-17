package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Cell
import de.htwg.se.reversi.model.gridComponent.GridInterface

import scala.math.sqrt

case class Grid(private val cells:Matrix[Cell]) extends GridInterface {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(0)))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Int):Grid = copy(cells.replaceCell(row, col, Cell(value)))
  def row(row:Int):House = ??? //House(cells.rows(row))
  def col(col:Int):House = House(cells.rows.map(row=>row(col)))
  def reset(row: Int, col: Int): Grid = ???

  override def createNewGrid: GridInterface = ???
  override def toString : String = ???
}

case class House(private val cells:Vector[Cell]) {
  def cell(index:Int):Cell = cells(index)
}