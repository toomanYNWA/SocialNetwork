package repositories

import models.User
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future

class UserRepository {

}
//def all(): Future[Seq[User]] = db.run(Users.result)

class UsersTable(tag: Tag) extends  Table[User](tag, "users"){
  def userId = column[Option[Long]]("USERID",O.PrimaryKey, O.AutoInc)

  def password = column[String]("PASSWORD")

  def name = column[String]("NAME")

  def username = column[String]("USERNAME")

  def * = (userId, password, name, username) <> (User.tupled, User.unapply)
}

