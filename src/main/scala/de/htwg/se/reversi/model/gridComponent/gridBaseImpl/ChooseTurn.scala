package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random
import scala.collection.concurrent.TrieMap

class ChooseTurn(size: Int) {
  def getNextTurnR(turns:List[Turn]):Turn = {
    var randNr = (new Random).nextInt()

    turns(randNr % turns.size)
  }

  def getNextTurnKI(turns:List[Turn], playerId:Int):Turn = {
    val turnsAnditsValue = TrieMap[Turn, Int]()
    turnsAnditsValue ++= turns.map(turn => (turn, 0))

    turnsAnditsValue.foreach(turn => {if (isWall(turn._1)) {turnsAnditsValue.replace(turn._1, turn._2, turn._2 + 1)}})

    val sorted = turnsAnditsValue.toSeq.sortBy(_._2).toMap
    //Todo: Hier unter den gleichwertigen noch eine Entscheidung treffen
    sorted.head._1
  }

  private def isWall(turn:Turn):Boolean = {
    if(turn.toRow == 0 || turn.toRow == size || turn.toCol == 0 || turn.toCol == size) {
      true
    }
    else {
      false
    }
  }
}
