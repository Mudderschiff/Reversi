package de.htwg.se.reversi.model.gridComponent.gridBaseImpl

import scala.util.Random

class ChooseTurn(grid: Grid) {

  def makeNextTurnBot(player:Int):Grid = {
    val turns = grid.getValidTurns(player)

    if(turns.filter(region5(_)).size > 0) {
      val t = Random.shuffle(turns.filter(region5(_))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region4(_)).size > 0) {
      val t = Random.shuffle(turns.filter(region4(_))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region3(_)).size > 0) {
      val t = Random.shuffle(turns.filter(region3(_))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region2(_)).size > 0) {
      val t = Random.shuffle(turns.filter(region2(_))).head
      grid.setTurnRC(player, t.toRow, t.toCol)
    }
    else if(turns.filter(region1(_)).size > 0) {
      val t = Random.shuffle(turns.filter(region1(_))).head
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

  private def region5(turn:Turn):Boolean = {
    if(turn.toRow == 0 && turn.toCol == 0) {
      true
    }
    else if(turn.toRow == 0 && turn.toCol == grid.size){
      true
    }
    else if(turn.toRow == grid.size && turn.toCol == 0){
      true
    }
    else if(turn.toRow == grid.size && turn.toCol == 0){
      true
    }
    else {
      false
    }
  }

  private def region4(turn:Turn):Boolean = {
    if(!region1(turn) && !region5(turn) && (turn.toRow == 0 || turn.toRow == grid.size || turn.toCol == 0 || turn.toCol == grid.size)) {
      true
    }
    else {
      false
    }
  }

  private def region3(turn:Turn):Boolean = {
    if(grid.size < 8) {
      false
    }
    else if(turn.toRow > 1 && turn.toRow < grid.size - 2 && turn.toCol > 1 && turn.toCol < grid.size - 2) {
      true
    }
    else {
      false
    }
  }

  private def region2(turn: Turn):Boolean = {
    if(grid.size < 8){
      false
    }
    else if(!region1(turn) && (turn.toRow == 1 || turn.toRow == grid.size - 2)) {
      true
    }
    else if(!region1(turn) && (turn.toCol == 1 || turn.toCol == grid.size - 2)) {
      true
    }
    else {
      false
    }
  }

  private def region1(turn: Turn):Boolean = {
    if(grid.size < 4) {
      false
    }
    else if(turn.toRow == 0 && (turn.toCol == 1 || turn.toCol == grid.size - 2)) {
      true
    }
    else if(turn.toRow == 1 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == grid.size -1 || turn.toCol == grid.size -2)){
      true
    }
    else if(turn.toRow == grid.size - 2 && (turn.toCol == 0 || turn.toCol == 1 || turn.toCol == grid.size -1 || turn.toCol == grid.size -2)){
      true
    }
    else if(turn.toRow == grid.size - 1 && (turn.toCol == 1 || turn.toCol == grid.size - 2)) {
      true
    }
    else {
      false
    }
  }
}