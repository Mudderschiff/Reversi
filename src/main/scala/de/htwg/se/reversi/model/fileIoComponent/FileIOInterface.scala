package de.htwg.se.reversi.model.fileIoComponent

import de.htwg.se.reversi.model.gridComponent.GridInterface

trait FileIOInterface {
  def load:Option[GridInterface]
  def save(grid:GridInterface):Unit
}
