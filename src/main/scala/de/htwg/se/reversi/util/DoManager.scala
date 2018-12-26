package de.htwg.se.reversi.util

class DoManager {
  private var stack: List[Command] = Nil
  def doStep(command: Command) = {
    stack = command :: stack
    command.doStep
  }
}
