package de.htwg.se.reversi.aview

//import com.typesafe.scalalogging.{LazyLogging, Logger}
//import de.htwg.se.sudoku.controller.controllerComponent.ControllerInterface
//import de.htwg.se.sudoku.controller.controllerComponent.GameStatus
//import de.htwg.se.sudoku.controller.controllerComponent.{CandidatesChanged, CellChanged, GridSizeChanged}


//import de.htwg.se.reversi.model.{Grid,GridCreator,Solver}
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.GridCreator
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid

class Tui {

  def processInputLine(input: String, grid:Grid):Grid = {
    input match {
      case "q" => grid
      case "n"=> new Grid(8)
      //case "r" => new GridCreator(9).createRandom(16)
      /*case "s" =>
        val (success, solvedGrid) = new Solver(grid).solve;
        if (success) println("Puzzle solved")else println("This puzzle could not be solved!")
        solvedGrid*/
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: value :: Nil => grid.set(row, column, value)
          case _ => grid
        }
      }
    }
  }
}

/*package de.htwg.se.reversi.aview

import de.htwg.se.reversi.controller.controllerComponent.GameStatus
import de.htwg.se.reversi.controller.controllerComponent.ControllerInterface

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {

}*/