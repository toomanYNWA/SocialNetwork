package repositories

import models.FriendRequest
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FriendRequestRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                       (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  val FriendRequests =  TableQuery[FriendRequestTable]


  def getAll: Future[Seq[FriendRequest]] = {
    db.run(FriendRequests.result)
  }

}

class FriendRequestTable(tag: Tag) extends  Table[FriendRequest](tag, "friendRequests"){

  def friendRequestId = column[Long]("FRIENDREQUESTID", O.PrimaryKey, O.AutoInc)

  def senderId = column[Long]("SENDERID")

  def recipientId = column[Long]("RECIPIENTID")

  def accepted = column[Boolean]("ACCEPTED")

  def * = (friendRequestId, senderId, recipientId, accepted) <> ((FriendRequest.apply _).tupled,FriendRequest.unapply)

}