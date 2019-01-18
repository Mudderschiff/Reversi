package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random

case class Bot() {
  def makeTurn(grid:Grid, player:Int): Grid = {
    val turns = grid.getValidTurns(player)

    if(turns.filter(region5(_, grid.size)).size > 0) {
      val t = Random.shuffle(turns.filter(region5(_, grid.size))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region4(_, grid.size)).size > 0) {
      val t = Random.shuffle(turns.filter(region4(_, grid.size))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region3(_, grid.size)).size > 0) {
      val t = Random.shuffle(turns.filter(region3(_, grid.size))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region2(_, grid.size)).size > 0) {
      val t = Random.shuffle(turns.filter(region2(_, grid.size))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region1(_, grid.size)).size > 0) {
      val t = Random.shuffle(turns.filter(region1(_, grid.size))).head
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

  private def region5(turn:Turn, size:Int):Boolean = {
    if(turn.toRow == 0 && turn.toCol == 0) {
      true
    }
    else if(turn.toRow == 0 && turn.toCol == size){
      true
    }
    else if(turn.toRow == size && turn.toCol == 0){
      true
    }
    else if(turn.toRow == size && turn.toCol == 0){
      true
    }
    else {
      false
    }
  }

  private def region4(turn:Turn, size:Int):Boolean = {
    if(!region1(turn, size) && !region5(turn, size) && (turn.toRow == 0 || turn.toRow == size || turn.toCol == 0 || turn.toCol == size)) {
      true
    }
    else {
      false
    }
  }

  private def region3(turn:Turn, size:Int):Boolean = {
    if(size < 8) {
      false
    }
    else if(turn.toRow > 1 && turn.toRow < size - 2 && turn.toCol > 1 && turn.toCol < size - 2) {
      true
    }
    else {
      false
    }
  }

  private def region2(turn:Turn, size:Int):Boolean = {
    if(size < 8){
      false
    }
    else if(!region1(turn, size) && (turn.toRow == 1 || turn.toRow == size - 2)) {
      true
    }
    else if(!region1(turn, size) && (turn.toCol == 1 || turn.toCol == size - 2)) {
      true
    }
    else {
      false
    }
  }

  private def region1(turn:Turn, size:Int):Boolean = {
    if(size < 4) {
      false
    }
    else if(turn.toRow == 0 && (turn.toCol == 1 || turn.toCol == size - 2)) {
      true
    }
    else if(turn.toRow == 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == size -1 || turn.toCol == size -2)){
      true
    }
    else if(turn.toRow == size - 2 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == size -1 || turn.toCol == size -2)){
      true
    }
    else if(turn.toRow == size - 1 && (turn.toCol == 1 || turn.toCol == size - 2)) {
      true
    }
    else {
      false
    }
  }

}
