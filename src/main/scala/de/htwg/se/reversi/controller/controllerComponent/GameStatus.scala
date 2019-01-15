package de.htwg.se.reversi.controller.controllerComponent

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, RESIZE, SET_Player1, SET_Player2, SET_Bot,BOT_ENABLE, BOT_DISABLE, NEW, LOADED, COULDNOTLOAD, SAVED, FINISHED = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    SET_Player1 -> "Cell set by Player 1",
    SET_Player2 -> "Cell set by Player 2",
    SET_Bot -> "Cell set by Bot (Player 2)",
    BOT_ENABLE -> "Bot enabled (Player 2)",
    BOT_DISABLE -> "Bot disabled (Player 2)",
    RESIZE -> "Game was resized",
    FINISHED -> "Finished",
    LOADED ->"A new Game was loaded",
    COULDNOTLOAD -> "The file could not be loaded",
    SAVED ->  "The Game was saved"
  )

  def message(gameStatus: GameStatus): String =  map(gameStatus)
}