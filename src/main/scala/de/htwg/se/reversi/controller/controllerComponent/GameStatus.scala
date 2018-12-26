package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, RESIZE, SET, NEW, LOADED, COULDNOTLOAD, SAVED, CANDIDATES, FINISHED = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A Cell was set",
    RESIZE -> "Game was resized",
    CANDIDATES -> "Showing candidates",
    FINISHED -> "Game successfully FINISHED",
    LOADED ->"A new Game was loaded",
    COULDNOTLOAD -> "The file could not be loaded",
    SAVED ->  "The Game was saved")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}