package de.htwg.se.reversi.aview

//import com.typesafe.scalalogging.{LazyLogging, Logger}
//import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
//import de.htwg.se.sudoku.controller.controllerComponent.GameStatus
//import de.htwg.se.sudoku.controller.controllerComponent.{CandidatesChanged, CellChanged, GridSizeChanged}


//import de.htwg.se.reversi.model.{Grid,GridCreator,Solver}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.GridCreator
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

class Tui {

  def processInputLine(input: String, grid:Grid):Grid = input match {
    case "q" => grid.createNewGrid
    case "n"=> new Grid(8).createNewGrid
    case "h1" => grid.highlight(1)
    case "h2" => grid.highlight(2)
    //case ""index :: value :: Nil => grid.setTurnIndex(value, index)
    /*case "rc" => {
      val args = input.split(" ").splitAt(2).
      grid.setTurnRC()
    }*/
    case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
      case row :: column :: value :: Nil => grid.setTurnRC(value, row, column)
      case _ => grid
    }
  }
}
