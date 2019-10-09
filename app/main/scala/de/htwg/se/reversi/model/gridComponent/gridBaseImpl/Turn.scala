package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

case class Turn(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int, dir: Direction.Value)

object Direction extends Enumeration {
  type Direction = Value
  val Up, Down, Left, Right, UpRight, UpLeft, DownRight, DownLeft = Value
}

