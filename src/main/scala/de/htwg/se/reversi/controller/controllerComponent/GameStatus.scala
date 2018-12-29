package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, RESIZE, SET_Player1, SET_Player2, SET_Bot, NEW, LOADED, COULDNOTLOAD, SAVED, CANDIDATES, FINISHED = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET_Player1 -> "Cell set by Player 1",
    SET_Player2 -> "Cell set by Player 2",
    SET_Bot -> "Cell set by Bot (Player2)",
    RESIZE -> "Game was resized",
    CANDIDATES -> "Showing candidates",
    FINISHED -> "Finished",
    LOADED ->"A new Game was loaded",
    COULDNOTLOAD -> "The file could not be loaded",
    SAVED ->  "The Game was saved")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}