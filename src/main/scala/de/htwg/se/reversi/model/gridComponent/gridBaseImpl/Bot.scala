package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random

case class Bot() {
  def makeTurn(grid:Grid, player:Int): Grid = {
    val turns = grid.getValidTurns(player)

    if(turns.filter(region5(_, grid.size - 1)).size > 0) {
      val t = Random.shuffle(turns.filter(region5(_, grid.size - 1))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region4(_, grid.size - 1)).size > 0) {
      val t = Random.shuffle(turns.filter(region4(_, grid.size - 1))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region3(_, grid.size - 1)).size > 0) {
      val t = Random.shuffle(turns.filter(region3(_, grid.size - 1))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region2(_, grid.size - 1)).size > 0) {
      val t = Random.shuffle(turns.filter(region2(_, grid.size - 1))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region1(_, grid.size - 1)).size > 0) {
      val t = Random.shuffle(turns.filter(region1(_, grid.size - 1))).head
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

  private def region5(turn:Turn, index:Int):Boolean = {
    if((turn.toRow == 0 && turn.toCol == 0) ||
      (turn.toRow == 0 && turn.toCol == index) ||
      (turn.toRow == index && turn.toCol == 0) ||
      (turn.toRow == index && turn.toCol == index)) {
      true
    }
    else {
      false
    }
  }

  private def region4(turn:Turn, index:Int):Boolean = {
    if(!region1(turn, index) && !region5(turn, index) &&
      (turn.toRow == 0 || turn.toRow == index || turn.toCol == 0 || turn.toCol == index)) {
      true
    }
    else {
      false
    }
  }

  private def region3(turn:Turn, index:Int):Boolean = {
    if(index < 7) {
      false
    }
    else if(turn.toRow > 1 && turn.toRow < index - 1 && turn.toCol > 1 && turn.toCol < index - 1) {
      true
    }
    else {
      false
    }
  }

  private def region2(turn:Turn, index:Int):Boolean = {
    if(index < 7){
      false
    }
    else if((!region1(turn, index) && (turn.toRow == 1 || turn.toRow == index - 1)) ||
      (!region1(turn, index) && (turn.toCol == 1 || turn.toCol == index - 1))) {
      true
    }
    else {
      false
    }
  }

  private def region1(turn:Turn, index:Int):Boolean = {
    if(index < 3) {
      false
    }
    else if((turn.toRow == 0 && (turn.toCol == 1 || turn.toCol == index - 1)) ||
      (turn.toRow == 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == index || turn.toCol == index - 1)) ||
      (turn.toRow == index - 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == index || turn.toCol == index - 1)) ||
      (turn.toRow == index && (turn.toCol == 1 || turn.toCol == index - 1))) {
      true
    }
    else {
      false
    }
  }
}
