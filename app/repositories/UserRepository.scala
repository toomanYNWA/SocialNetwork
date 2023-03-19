package repositories

import models.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
 import profile.api._
  val Users = TableQuery[UsersTable]//doslovno baza

  def getAll: Future[Seq[User]] = {
    db.run(Users.result) //db.run poziva bazu i pretvara u model koji sam napravio
  }                                              // .map radi kao for, .filer prolazi i uzima samo zahtevane
                                                // .result vraca sve

  def add(user: User): Future[String] = {
    db.run(((Users returning Users.map(_.userId)) += user).map(newId => user.copy(userId = newId)))
      .map(res => "User successfully registered").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

}
//def all(): Future[Seq[User]] = db.run(Users.result)

class UsersTable(tag: Tag) extends  Table[User](tag, "users"){
  def userId = column[Option[Long]]("USERID",O.PrimaryKey, O.AutoInc)

  def password = column[String]("PASSWORD")

  def name = column[String]("NAME")

  def username = column[String]("USERNAME")

  def * = (userId, name, username,password) <> ((User.apply _).tupled, User.unapply)
}

