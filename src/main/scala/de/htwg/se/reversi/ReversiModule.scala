package de.htwg.se.reversi

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.reversi.controller.controllerComponent._
import de.htwg.se.reversi.model.fileIoComponent._
import de.htwg.se.reversi.model.gridComponent.GridInterface
import de.htwg.se.reversi.model.gridComponent.gridAdvancedImpl.Grid
import net.codingwell.scalaguice.ScalaModule

class ReversiModule extends AbstractModule with ScalaModule {
  val defaultSize: Int = 8

  def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(8))
    //bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
    //bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
    bind[FileIOInterface].to[fileIoMicroServiceImpl.FileIO]
  }
}


