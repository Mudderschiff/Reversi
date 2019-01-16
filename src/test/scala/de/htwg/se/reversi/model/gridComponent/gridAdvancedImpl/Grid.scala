package de.htwg.se.reversi.model.gridComponent.gridAdvancedImpl

import org.scalatest.{Matchers, WordSpec}

class HouseSpec extends WordSpec with Matchers {
  "A AdvancedGrid is the playingfield of Reversi. A Grid" when {
    "created properly" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(3)
      val normalGrid = new Grid(8)
      val bigGrid = new Grid(16)
    }
  }
}
