package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class Turn(var fromRow:Int, var fromCol:Int, var toRow:Int, var toCol:Int, var dir:Direction.Value)

object Direction extends Enumeration {
  type Direction = Value
  val Up, Down, Left, Right, UpRight, UpLeft, DownRight, DownLeft = Value
}
