package de.htwg.se.reversi.model.gridComponent.gridAdvancedImpl

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.{GridCreator, Grid => BaseGrid}

class Grid @Inject()(@Named("DefaultSize") size: Int) extends BaseGrid(size) {
  override def createNewGrid: GridInterface = (new GridCreator).createGrid(size)
}
