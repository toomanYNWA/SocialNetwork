package services

import models.exception.FriendRequestException
import models.{AddFriend, FriendId, FriendRequest, FriendRequestResponse, User}
import play.api.libs.json.JsResult.Exception
import repositories.FriendRequestRepository

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FriendRequestService @Inject()(friendRequestRepository: FriendRequestRepository)
                                    (implicit ec: ExecutionContext){



  def getAll: Future[Seq[FriendRequest]] = {
    friendRequestRepository.getAll
  }

  def sendRequest(newRequest: AddFriend, user: User) = {
    val f = FriendRequest(-1,user.userId,newRequest.recipientId, accepted = false, LocalDateTime.now().withNano(0))
    friendRequestRepository.getBySenderRecipientId(newRequest.recipientId,user.userId).flatMap{
      case Some(_) => throw new FriendRequestException("Request already sent!")
      case None => friendRequestRepository.getBySenderRecipientIdReversed(newRequest.recipientId,user.userId).flatMap{
        case Some(_) => throw new FriendRequestException("Request is pending!")
        case None => friendRequestRepository.sendRequest(f)
      }

    }

 }

  def answerFR(answerFR: FriendRequestResponse):Future[Unit] =  {
    if(!answerFR.accepted){
      friendRequestRepository.deleteFR(answerFR.friendRequestId)
    } else {
       friendRequestRepository.getFRById(answerFR.friendRequestId).map{
         case None => new FriendRequestException("No such user!")
         case Some(obj) =>
           friendRequestRepository.updateFR(obj)
       }
    }
  }

  def deleteFR(id: Long): Future[Unit] = {
    friendRequestRepository.deleteFR(id)
  }

  def getMyFR(user: User) = {
    friendRequestRepository.getMyFR(user.userId)
  }

  def getSpecialFR(idFriend: Long, idUser: Long) = {
    friendRequestRepository.getSpecialFR(idFriend, idUser)
  }
}
