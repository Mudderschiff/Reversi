package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{ Guice, Inject }
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface
//import de.htwg.se.reversi.util.UndoManager
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.reversi.model.playerComponent.Player

import scala.swing.Publisher
import scala.util.Random

class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  val player1 = new Player(1)
  val player2 = new Player(2)
  var activePlayer = Random.shuffle(List(player1.playerId,player2.playerId)).head
  //var showAllCandidates: Boolean = false
  //private val undoManager = new UndoManager
  val injector = Guice.createInjector(new ReversiModule)
  val fileIo = injector.instance[FileIOInterface]

  def createEmptyGrid: Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    publish(new CellChanged)
  }

  def resize(newSize: Int): Unit = {
    newSize match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal")).highlight(activePlayer)
      case _ =>
    }
    gameStatus = RESIZE
    publish(new GridSizeChanged(newSize))
  }

  override def score(): (Int, Int) = grid.score()

  def set(row: Int, col: Int, playerId: Int): Unit = {
    var grid = this.grid
    println(activePlayer)
    //this.grid = this.grid.setTurnRC(playerId,row,col)
    if(!grid.checkChange(grid.setTurnRC(playerId,row,col))) {
      changePlayer()
    }
    //println(activePlayer)
    if(activePlayer == 1) {
      this.grid = this.grid.setTurnRC(playerId,row,col).highlight(2)
    } else {
      this.grid = this.grid.setTurnRC(playerId,row,col).highlight(1)
    }
    gameStatus = SET
    publish(new CellChanged)
  }
  //def isGiven(row: Int, col: Int): Boolean = grid.cell(row, col).given

  def changePlayer(): Unit = if(activePlayer == 1) activePlayer = player2.playerId else if (activePlayer == 2) activePlayer = player1.playerId

  override def createNewGrid: Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal")).highlight(activePlayer)
      case _ =>
    }
    if(activePlayer == 1) {
      grid = grid.createNewGrid.highlight(2)
    } else {
      grid = grid.createNewGrid.highlight(1)
    }
    grid = grid.createNewGrid.highlight(activePlayer)
    gameStatus = NEW
    publish(new CellChanged)
  }

  def gridToString: String = grid.toString

  override def highlight(playerId: Int): Unit = {
    gameStatus = CANDIDATES
    publish(new CandidatesChanged)
  }
/*
  def set(row: Int, col: Int, value: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    gameStatus = SET
    publish(new CellChanged)
  }

  def solve: Unit = {
    undoManager.doStep(new SolveCommand(this))
    gameStatus = SOLVED
    publish(new CellChanged)
  }*/

  def save: Unit = {
    fileIo.save(grid)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  def load: Unit = {
    val gridOption = fileIo.load
    gridOption match {
      case None => {
        createEmptyGrid
        gameStatus = COULDNOTLOAD
      }
      case Some(_grid) => {
        grid = _grid
        gameStatus = LOADED
      }
    }
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int) = grid.cell(row, col)

  //def isGiven(row: Int, col: Int): Boolean = grid.cell(row, col).given
  //def isSet(row: Int, col: Int): Boolean = grid.cell(row, col).isSet
  //def available(row: Int, col: Int): Set[Int] = grid.available(row, col)
  /*
  def showCandidates(row: Int, col: Int): Unit = {
    grid = grid.setShowCandidates(row, col)
    gameStatus = CANDIDATES
    publish(new CandidatesChanged)
  }*/

  //def isShowCandidates(row: Int, col: Int): Boolean = grid.cell(row, col).showCandidates
  def gridSize: Int = grid.size
  //def blockSize: Int = Math.sqrt(grid.size).toInt
  //def isShowAllCandidates: Boolean = showAllCandidates
  /*
  def toggleShowAllCandidates: Unit = {
    showAllCandidates = !showAllCandidates
    gameStatus = CANDIDATES
    publish(new CellChanged)
  }*/
  //def isHighlighted(row: Int, col: Int): Boolean = grid.isHighlighted(row, col)
  def statusText: String = GameStatus.message(gameStatus)
  /*
  def highlight(index: Int): Unit = {
    grid = grid.highlight(index)
    publish(new CellChanged)
  }

  override def setGiven(row: Int, col: Int, value: Int): Unit = {
    grid = grid.setGiven(row, col, value)
  }

  override def setShowCandidates(row: Int, col: Int): Unit = {
    grid = grid.setShowCandidates(row, col)
  }
  */
  //override def checkChange(gridnew: GridInterface): Boolean = grid.checkChange(gridnew)
}
