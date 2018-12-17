package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, SET, FINISH, NEW= Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A Cell was set",
    FINISH -> "Game has finished")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}