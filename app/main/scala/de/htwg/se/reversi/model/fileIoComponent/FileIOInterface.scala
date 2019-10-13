package de.htwg.wt.reversi.model.fileIoComponent

import de.htwg.wt.reversi.model.gridComponent.GridInterface

trait FileIOInterface {
  def load: Option[GridInterface]

  def loadPlayer: Int

  def save(grid: GridInterface): Unit

  def savePlayer(activePlayer: Int): Unit
}
