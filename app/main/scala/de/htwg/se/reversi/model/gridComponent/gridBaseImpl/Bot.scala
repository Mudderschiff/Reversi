package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

class Bot extends BotTemplate() {

  override def makeTurn(grid: Grid, player: Int): Grid = {
    val turns = grid.getValidTurns(player)

    if (turns.count(region5(_, grid.size - 1)) > 0) {
      val t = getRandomTurn(turns.filter(region5(_, grid.size - 1)))
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if (turns.count(region4(_, grid.size - 1)) > 0) {
      val t = getRandomTurn(turns.filter(region4(_, grid.size - 1)))
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if (turns.count(region3(_, grid.size - 1)) > 0) {
      val t = getRandomTurn(turns.filter(region3(_, grid.size - 1)))
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if (turns.count(region2(_, grid.size - 1)) > 0) {
      val t = getRandomTurn(turns.filter(region2(_, grid.size - 1)))
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if (turns.count(region1(_, grid.size - 1)) > 0) {
      val t = getRandomTurn(turns.filter(region1(_, grid.size - 1)))
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else {
      grid
    }
  }

  //5 1 4 4 4 4 1 5
  //1 1 2 2 2 2 1 1
  //4 2 3 3 3 3 2 4
  //4 2 3 3 3 3 2 4
  //4 2 3 3 3 3 2 4
  //4 2 3 3 3 3 2 4
  //1 1 2 2 2 2 1 1
  //5 1 4 4 4 4 1 5

  private def region4(turn: Turn, maxIndex: Int): Boolean =
    if (!region1(turn, maxIndex) && !region5(turn, maxIndex) &&
      (turn.toRow == 0 || turn.toRow == maxIndex || turn.toCol == 0 || turn.toCol == maxIndex)) true else false

  private def region5(turn: Turn, maxIndex: Int): Boolean = if ((turn.toRow == 0 && turn.toCol == 0) ||
    (turn.toRow == 0 && turn.toCol == maxIndex) ||
    (turn.toRow == maxIndex && turn.toCol == 0) ||
    (turn.toRow == maxIndex && turn.toCol == maxIndex)) true else false



  private def region1(turn: Turn, maxIndex: Int): Boolean =
    if ((turn.toRow == 0 && (turn.toCol == 1 || turn.toCol == maxIndex - 1)) ||
    (turn.toRow == 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == maxIndex || turn.toCol == maxIndex - 1)) ||
    (turn.toRow == maxIndex - 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == maxIndex || turn.toCol == maxIndex - 1)) ||
    (turn.toRow == maxIndex && (turn.toCol == 1 || turn.toCol == maxIndex - 1))) true else false

  private def region3(turn: Turn, maxIndex: Int): Boolean =
    if (maxIndex >= 7 && turn.toRow > 1 && turn.toRow < maxIndex - 1 && turn.toCol > 1 && turn.toCol < maxIndex - 1)
      true else false


  private def region2(turn: Turn, maxIndex: Int): Boolean =
    if (maxIndex >= 7 && (!region1(turn, maxIndex) && (turn.toRow == 1 || turn.toRow == maxIndex - 1)) ||
      (!region1(turn, maxIndex) && (turn.toCol == 1 || turn.toCol == maxIndex - 1))) true else false

}
