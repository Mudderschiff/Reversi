package de.htwg.wt.reversi

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.wt.reversi.controller.controllerComponent._
import de.htwg.wt.reversi.model.fileIoComponent._
import de.htwg.wt.reversi.model.daoComponent._
import de.htwg.wt.reversi.model.gridComponent.GridInterface
import de.htwg.wt.reversi.model.gridComponent.gridAdvancedImpl.Grid
import net.codingwell.scalaguice.ScalaModule

class ReversiModule extends AbstractModule with ScalaModule {
  val defaultSize: Int = 8

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[GridInterface].to[Grid]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    bind[GridInterface].annotatedWithName("tiny").toInstance(new Grid(1))
    bind[GridInterface].annotatedWithName("small").toInstance(new Grid(4))
    bind[GridInterface].annotatedWithName("normal").toInstance(new Grid(8))
    //bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
    //bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
    bind[FileIOInterface].to[fileIoMicroServiceImpl.FileIO]
    bind[DAOInterface].to[slickImpl.gridDao]
  }
}


