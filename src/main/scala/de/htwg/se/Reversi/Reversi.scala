package de.htwg.se.Reversi
import de.htwg.se.Reversi.model.Player

object Reversi {
  def main(args: Array[String]) : Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}