package de.htwg.se.reversi

import de.htwg.se.reversi.model.playerComponent.Player
import de.htwg.se.reversi.aview
import de.htwg.se.reversi.aview.Tui

object Reversi {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}
