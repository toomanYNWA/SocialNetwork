package repositories

import models.{AddFriend, FriendRequest}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import java.time.LocalDateTime
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

  def sendRequest(friendRequest: FriendRequest):Future[FriendRequest] = {
    db.run(((FriendRequests returning FriendRequests.map(_.friendRequestId)) += friendRequest).map(newId => friendRequest.copy(friendRequestId = newId)))
  }

  def getBySenderRecipientId(recipientId: Long, senderId: Long): Future[Option[FriendRequest]] = {
    db.run(FriendRequests.filter(_.recipientId === recipientId).filter(_.senderId === senderId).result.headOption)
  }

  def getBySenderRecipientIdReversed(recipientId: Long, senderId: Long):Future[Option[FriendRequest]] = {
    db.run(FriendRequests.filter(_.recipientId === senderId).filter(_.senderId === recipientId).result.headOption)
  }

  def deleteFR(id: Long): Future[Unit] = {
    db.run(FriendRequests.filter(_.friendRequestId === id).delete)
      .map(_ => ())
  }

  def getFRById(friendRequestId: Long): Future[Option[FriendRequest]] = {
    db.run(FriendRequests.filter(_.friendRequestId === friendRequestId).result.headOption)
  }

  def getMyFR(userId: Long):Future[Seq[FriendRequest]] = {
    db.run(FriendRequests.filter(_.recipientId === userId).result)
  }

  def updateFR(obj: FriendRequest) = {
    db.run(FriendRequests.filter(_.friendRequestId === obj.friendRequestId).map(upd => upd.accepted).update(true))
  }

  def getSpecialFR(idFriend: Long, idUser: Long):Future[Option[FriendRequest]] = {
    db.run(FriendRequests.filter(friendRequest=> friendRequest.senderId === idFriend || friendRequest.senderId === idUser)
      .filter(obj=> obj.recipientId === idFriend || obj.recipientId === idUser).result.headOption)
  }
}

class FriendRequestTable(tag: Tag) extends  Table[FriendRequest](tag, "friendRequests"){

  def friendRequestId = column[Long]("FRIENDREQUESTID", O.PrimaryKey, O.AutoInc)

  def senderId = column[Long]("SENDERID")

  def recipientId = column[Long]("RECIPIENTID")

  def accepted = column[Boolean]("ACCEPTED")

  def sentAt = column[LocalDateTime]("SENTAT")

  def * = (friendRequestId, senderId, recipientId, accepted, sentAt) <> ((FriendRequest.apply _).tupled,FriendRequest.unapply)

}