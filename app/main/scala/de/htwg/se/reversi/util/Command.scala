package de.htwg.wt.reversi.util

trait Command {
  def doStep: Unit

  def undoStep: Unit

  def redoStep: Unit
}

