package de.htwg.se.reversi.model.gridComponent

trait GridInterface {

  def cell(row: Int, col: Int): CellInterface
  def set(row:Int, col:Int, value:Int): GridInterface
  def reset(row:Int, col:Int): GridInterface
  def createNewGrid: GridInterface
  def row(row:Int): GridInterface
  def size: Int

  //def isfinished:Boolean

}

trait CellInterface {
  def value:Int
  def isSet: Boolean
}