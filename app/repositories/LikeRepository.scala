package repositories

import models.{Like, LikeUnlikePost}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LikeRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile]{



  //import profile.api._
  val Likes = TableQuery[LikesTable]

  def add(like: Like): Future[Unit] = {
    db.run(((Likes returning Likes.map(_.likeId)) += like).map(newId => like.copy(likeId = newId)))
      .map(_ => ())
  }

  def delete(l: Like): Future[Unit] = {
    db.run(Likes.filter(_.postId === l.postId ).filter(_.userId === l.userId).delete)
      .map(_ => ())
  }

  def getLike(l: Like): Future[Option[Like]] = {
    db.run(Likes.filter(_.postId === l.postId).filter(_.userId === l.userId).result.headOption)
  }

  def getAll(): Future[Seq[Like]] = {
    db.run(Likes.result)
  }
}

class LikesTable(tag: Tag) extends Table[Like](tag, "likes"){

  def likeId = column[Long]("LIKEID", O.PrimaryKey, O.AutoInc)

  def postId = column[Long]("POSTID")

  def userId = column[Long]("USERID")

  def * = (likeId, postId, userId) <> ((Like.apply _).tupled, Like.unapply)
}