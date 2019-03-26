package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random

trait BotTemplate {
  def makeTurn(grid: Grid, player: Int): Grid

  def getRandomTurn(turns: List[Turn]): Turn = {
    Random.shuffle(turns).head
  }
}
