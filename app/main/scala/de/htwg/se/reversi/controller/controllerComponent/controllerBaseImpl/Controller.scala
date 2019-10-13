package de.htwg.wt.reversi.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.wt.reversi.ReversiModule
import de.htwg.wt.reversi.controller.controllerComponent.GameStatus._
import de.htwg.wt.reversi.controller.controllerComponent._
import de.htwg.wt.reversi.model.daoComponent.DAOInterface
import de.htwg.wt.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.wt.reversi.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.wt.reversi.model.playerComponent.Player
import de.htwg.wt.reversi.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher
import scala.util.Random

class Controller @Inject()(var grid: GridInterface) extends ControllerInterface with Publisher {
  val injector: Injector = Guice.createInjector(new ReversiModule)
  val player1 = Player(1)
  val player2 = Player(2)
  private val undoManager = new UndoManager
  var gameStatus: GameStatus = IDLE
  var fileIo: FileIOInterface = injector.instance[FileIOInterface]
  var db: DAOInterface = injector.instance[DAOInterface]
  var activePlayer: Int = randomActivePlayer()
  var botPlayer = false

  def enableBot(): Unit = {
    botPlayer = true
    gameStatus = BOT_ENABLE
    publish(new BotStatus)
  }

  def disableBot(): Unit = {
    botPlayer = false
    gameStatus = BOT_DISABLE
    publish(new BotStatus)
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
    publish(GridSizeChanged(newSize))
  }

  override def createNewGrid(): Unit = {
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

  def getActivePlayer(): Int = activePlayer

  def randomActivePlayer(): Int = Random.shuffle(List(player1.playerId, player2.playerId)).head

  def evaluateGame(): Int = grid.evaluateGame()

  override def score(): (Int, Int) = grid.score()

  def finish(): Unit = {
    if (grid.finish(getActivePlayer())) {
      gameStatus = FINISHED
      publish(new Finished)
    }
  }

  def set(row: Int, col: Int, playerId: Int): Unit = {
    undoManager.doStep(new SetCommand(playerId, row, col, this))
    publish(new CellChanged)
  }

  def bot(): Unit = {
    if (activePlayer == 2) {
      undoManager.doStep(new BotCommand(this))
      changePlayer()
      gameStatus = SET_Bot
      publish(new CellChanged)
    }
  }

  def changePlayer(): Unit = if (activePlayer == 1) activePlayer = player2.playerId else activePlayer = player1.playerId

  def save(): Unit = {
    fileIo.save(grid)
    fileIo.savePlayer(activePlayer)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  def load(): Unit = {
    val gridOption = fileIo.load
    gridOption match {
      case None =>
        createEmptyGrid()
        gameStatus = COULDNOTLOAD
      case Some(_grid) =>
        grid = _grid
        gameStatus = LOADED
    }
    activePlayer = fileIo.loadPlayer
    publish(new CellChanged)
  }

  def createEmptyGrid(): Unit = {
    grid.size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
      case 4 => grid = injector.instance[GridInterface](Names.named("small"))
      case 8 => grid = injector.instance[GridInterface](Names.named("normal"))
      case _ =>
    }
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int): CellInterface = grid.cell(row, col)

  def gridSize: Int = grid.size

  def statusText: String = GameStatus.message(gameStatus)

  def gridToString: String = grid.toString

  def undo: Unit = {
    if (botState()) {
      undoManager.undoStep
      undoManager.undoStep
    } else {
      undoManager.undoStep
    }

    gameStatus = UNDO
    publish(new CellChanged)
  }

  def botState(): Boolean = botPlayer

  def redo: Unit = {
    undoManager.redoStep
    gameStatus = REDO
    publish(new CellChanged)
  }

  def saveGrid(grid: String, player: Int): Unit = db.saveGrid(grid,activePlayer)

  def getGridById(id: Int): (Int, Int, String)  = db.getGridById(id)

  def getAllGrids: List[(Int,Int, String)] = db.getAllGrids

  def deleteGridById(id: Int): Boolean = db.deleteGridById(id)

}
