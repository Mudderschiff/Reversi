package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, SET, FINISH, NEW, OddNumber= Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A Cell was set",
    FINISH -> "Game has finished",
    OddNumber -> "Couldn't create field. The number must be eval.")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}