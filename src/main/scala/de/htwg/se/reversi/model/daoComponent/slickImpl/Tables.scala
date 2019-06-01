import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

class Grids(tag: Tag) extends Table[(Int, Int, String)](tag, "Grids") {
  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey)
  def player: Rep[Int] = column[Int]("Player")
  def grid: Rep[String] = column[String]("Grid")

  def * : ProvenShape[(Int, Int, String)] = (id,player,grid)
}