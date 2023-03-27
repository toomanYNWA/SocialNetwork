package repositories

import auth.JwtUtil
import models.exception.LoginException
import models.{LoggedUser, User}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val Users = TableQuery[UsersTable] //doslovno baza

  def getAll: Future[Seq[User]] = {
    db.run(Users.result) //db.run poziva bazu i pretvara u model koji sam napravio
  } // .map radi kao for, .filer prolazi i uzima samo zahtevane
  // .result vraca sve

  def add(user: User): Future[User] = {
    db.run(((Users returning Users.map(_.userId)) += user).map(newId => user.copy(userId = newId))) // copy omogucava menjanje polja
  }

  def getByUsername(username: String): Future[Option[User]] = {
    db.run(Users.filter(_.username === username).result.headOption)
  }

  def update(user: User): Future[User] = {
    db.run(Users.filter(_.userId === user.userId).map(upd => (upd.name, upd.username)).update((user.name, user.username)))
      .map(res => user)
  }


  def searchByUsernameOrName(text: String): Future[Seq[User]] = {
    db.run(Users.filter(user => user.username.like(s"${text}%") || user.name.like(s"${text}%")).result)
  }

  def login(loggedUser: LoggedUser): Future[String] = {
    val jwt = JwtUtil.createToken(loggedUser)

    db.run(Users.result.head.map(res => jwt)).recover {
      case ex: Exception => "Wrong password"
    }

  }

}

class UsersTable(tag: Tag) extends  Table[User](tag, "users"){
  def userId = column[Option[Long]]("USERID",O.PrimaryKey, O.AutoInc)

  def password = column[String]("PASSWORD")

  def name = column[String]("NAME")

  def username = column[String]("USERNAME")

  def * = (userId, name, username,password) <> ((User.apply _).tupled, User.unapply)
}

