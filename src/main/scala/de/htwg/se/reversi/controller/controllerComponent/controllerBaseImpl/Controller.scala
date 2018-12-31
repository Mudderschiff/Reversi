package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{ Guice, Inject }
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.reversi.model.playerComponent.Player

import scala.swing.Publisher
import scala.util.Random

class Controller @Inject() (var grid: GridInterface) extends ControllerInterface with Publisher {

  var gameStatus: GameStatus = IDLE
  val injector = Guice.createInjector(new ReversiModule)
  val fileIo = injector.instance[FileIOInterface]

  val player1 = new Player(1)
  val player2 = new Player(2)
  var activePlayer = randomActivePlayer()
  var botplayer = false


  def getActivePlayer(): Int = activePlayer
  def randomActivePlayer(): Int = Random.shuffle(List(player1.playerId,player2.playerId)).head
  def changePlayer(): Unit = if(activePlayer == 1) activePlayer = player2.playerId else activePlayer = player1.playerId

  def enableBot(): Unit = {
    botplayer = true
    gameStatus = BOT_ENABLE
    publish(new BotStatus)
  }
  def disableBot(): Unit = {
    botplayer = false
    gameStatus = BOT_DISABLE
    publish(new BotStatus)
  }
  def botstate(): Boolean = botplayer

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
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    activePlayer = randomActivePlayer()
    grid = grid.createNewGrid.highlight(getActivePlayer())
    gameStatus = RESIZE
    publish(new GridSizeChanged(newSize))
  }

  override def createNewGrid: Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    activePlayer = randomActivePlayer()
    grid = grid.createNewGrid.highlight(getActivePlayer())
    gameStatus = NEW
    publish(new CellChanged)
  }

  def evaluateGame(): Int = grid.evaluateGame()
  override def score(): (Int, Int) = grid.score()
  def finish(): Unit = {
    if(grid.finish(getActivePlayer())) {
      gameStatus = FINISHED
      publish(new Finished)
    }
  }

  def set(row: Int, col: Int, playerId: Int): Unit = {
    if(grid.checkChange(grid.setTurnRC(playerId,row,col))) {
      if (playerId == 1) {
        grid = grid.setTurnRC(playerId,row,col).highlight(2)
        gameStatus = SET_Player1
      } else {
        grid = grid.setTurnRC(playerId,row,col).highlight(1)
        gameStatus = SET_Player2
      }
      changePlayer()
      publish(new CellChanged)
    }
  }

  def bot: Unit = {
    if(activePlayer == 2) {
      if(grid.checkChange(grid.makeNextTurnRandom(activePlayer))) {
        grid = grid.makeNextTurnRandom(activePlayer).highlight(1)
        changePlayer()
        gameStatus = SET_Bot
        publish(new CellChanged)
      }
    }
  }

  // currently always higlight so unneded
  override def highlight(playerId: Int): Unit = {
    gameStatus = CANDIDATES
    publish(new CandidatesChanged)
  }

  def save: Unit = {
    fileIo.save(grid)
    fileIo.savePlayer(activePlayer)
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
    activePlayer = fileIo.loadPlayer
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int) = grid.cell(row, col)
  def gridSize: Int = grid.size
  def statusText: String = GameStatus.message(gameStatus)
  def gridToString: String = grid.toString

  override def cellToString(row: Int, col: Int): String = cell(row,col).toString
}
