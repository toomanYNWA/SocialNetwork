package repositories

import models.{CreatePost, Post, UserDetails}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._

import java.time.{LocalDate, LocalDateTime}


class PostRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._
  val Posts = TableQuery[PostsTable]

  def getAll:Future[Seq[Post]] = {
    db.run(Posts.result)
  }
//  def add(cPost: CreatePost): Future[Option[Post]] = {
//    db.run(((Posts returning Posts.map(_.postId)) += cPost).map(newId => Some(Post.createPostToPost(cPost).copy(postId = newId))))
//  }

  def insert(post: Post): Future[Post] =
    db.run(((Posts returning Posts.map(_.postId)) += post).map(newId => post.copy(postId = newId)))

}


class PostsTable(tag: Tag) extends  Table[Post](tag, "posts"){

  def postId = column[Long]("POSTID",O.PrimaryKey, O.AutoInc)

  def userId = column[Long]("USERID")

  def postedAt = column[LocalDateTime]("POSTEDAT")

  def text = column[String]("TEXT")

  def * = (postId, userId,postedAt, text) <> ((Post.apply _).tupled, Post.unapply)
}