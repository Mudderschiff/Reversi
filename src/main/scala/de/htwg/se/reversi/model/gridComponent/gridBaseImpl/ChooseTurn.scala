package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random
import scala.collection.concurrent.TrieMap

class ChooseTurn(grid: Grid) {

  def makeNextTurnRandom(player:Int):Grid = {
    grid.setTurn(Random.shuffle(grid.getValidTurns(player)).head, player)
  }

  def makeNextTurnKI(player:Int):Grid = {
    val turnsAnditsValue = TrieMap[Turn, Int]()
    turnsAnditsValue ++= grid.getValidTurns(player).map(turn => (turn, 0))

    turnsAnditsValue.foreach(turn => {if (isWall(turn._1)) {turnsAnditsValue.replace(turn._1, turn._2, turn._2 + 1)}})

    val sorted = turnsAnditsValue.toSeq.sortBy(_._2).toMap
    //Todo: Hier unter den gleichwertigen noch eine Entscheidung treffen
    grid.setTurn(sorted.head._1, player);
  }

  private def isWall(turn:Turn):Boolean = {
    if(turn.toRow == 0 || turn.toRow == grid.size || turn.toCol == 0 || turn.toCol == grid.size) {
      true
    }
    else {
      false
    }
  }
}
