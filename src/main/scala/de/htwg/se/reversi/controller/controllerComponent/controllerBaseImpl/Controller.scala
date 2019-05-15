package de.htwg.se.reversi.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.reversi.ReversiModule
import de.htwg.se.reversi.controller.controllerComponent.GameStatus._
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent.FileIOInterface
import de.htwg.se.reversi.model.gridComponent.{CellInterface, GridInterface}
import de.htwg.se.reversi.model.playerComponent.Player
import de.htwg.se.reversi.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher
import scala.util.Random

import akka.actor.ActorSystem
import akka.http.javadsl.server.directives.RouteDirectives
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer

class Controller @Inject()(var grid: GridInterface) extends ControllerInterface with Publisher {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  val groute: Route =
    get {
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>Controller</h1>"))
      } ~
        path("controller") {

          gridtoHtml("xD")
        } ~
        path("controller" / "gridSize") {
          gridtoHtml(gridSize.toString)
        } ~
        path("controller" / "createEmptyGrid") {
          createEmptyGrid()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "createNewGrid") {
          createNewGrid()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "save") {
          save()
          gridtoHtml("success")
        } ~
        path("controller" / "load") {
          load()
          gridtoHtml("success")
        } ~
        path("controller" / "score") {
          score()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "gameStatus") {
          gridtoHtml(gameStatus.toString)
        } ~
        path("controller" / "finish") {
          finish()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "gridToString") {
          gridtoHtml(gridToString)
        } ~
        path("controller" / "evaluateGame") {
          gridtoHtml(evaluateGame().toString)
        } ~
        path("controller" / "botState") {
          gridtoHtml(botState().toString)
        } ~
        path("controller" / "enableBot") {
          enableBot()
          gridtoHtml("success")
        } ~
        path("controller" / "disableBot") {
          disableBot()
          gridtoHtml("success")
        } ~
        path("controller" / "bot") {
          bot()
          gridtoHtml("success")
        } ~
        path("controller" / "statusText") {
          gridtoHtml(statusText)
        } ~
        path("controller" / "undo") {
          undo
          gridtoHtml("success")
        } ~
        path("controller" / "redo") {
          redo
          gridtoHtml("success")
        }
    } ~
      put {
        path("controller" / "resize") {
          parameter("newSize".as[Int]) { news =>
            resize(news)
            gridtoHtml(gridToString)
          }

        } ~
          path("controller" / "set") {
            parameter("row".as[Int], "col".as[Int], "value".as[Int]) { (row, col, value) =>
              set(row, col, value)
              gridtoHtml(gridToString)
            }
          } ~
          path("controller" / "cell") {
            parameter("row".as[Int], "col".as[Int]) { (row, col) =>
              gridtoHtml(cell(row, col).toString)
            }
          }
      }

  def gridtoHtml(xd: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Controller</h1>" + "<pre>"+xd+"</pre>"))
  }

  val bindingFuture = Http().bindAndHandle(groute, "localhost", 8070)

  val injector: Injector = Guice.createInjector(new ReversiModule)
  val player1 = Player(1)
  val player2 = Player(2)
  private val undoManager = new UndoManager
  var gameStatus: GameStatus = IDLE
  var fileIo: FileIOInterface = injector.instance[FileIOInterface]
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
}
