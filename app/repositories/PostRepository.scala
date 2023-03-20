package repositories

import akka.http.scaladsl.model.DateTime
import models.{Post, UserDetails}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import java.util.Date
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDate


class PostRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._
  val Posts = TableQuery[PostsTable]
}


class PostsTable(tag: Tag) extends  Table[Post](tag, "posts"){

  def postId = column[Option[Long]]("POSTID",O.PrimaryKey, O.AutoInc)

  def userId = column[Long]("USERID")

  def username = column[String]("USERNAME")

  def name = column[String]("NAME")

  def postedAt = column[LocalDate]("POSTEDAT")

  def text = column[String]("TEXT")

  def liked = column[Boolean]("LIKED")

  def likeCount = column[Long]("LIKECOUNT")


  def * = (postId, userId,  username, name,postedAt, text, liked, likeCount) <> ((Post.apply _).tupled, Post.unapply)
}