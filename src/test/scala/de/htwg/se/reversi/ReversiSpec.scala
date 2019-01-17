package de.htwg.se.reversi

import org.scalatest.{Matchers, WordSpec}

class ReversiSpec extends WordSpec with Matchers {

  "The Reversi main class" should {
    "accept text input as argument without readline loop, to test it from command line " in {
      Reversi.main(Array[String]("s"))
    }
  }

}
