package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, NEW, SET, CANDIDATES, FINISH, LOADED, COULDNOTLOAD, SAVED = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET -> "A Cell was set",
    CANDIDATES -> "Showing candidates",
    FINISH -> "Game has finished",
    LOADED ->"A new Game was loaded",
    COULDNOTLOAD -> "The file could not be loaded",
    SAVED ->  "The Game was saved"
  )

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}