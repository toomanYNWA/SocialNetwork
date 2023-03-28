package services

import models.exception.FriendRequestException
import models.{AddFriend, FriendRequest}
import repositories.FriendRequestRepository

import java.time.LocalDateTime
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class FriendRequestService @Inject()(friendRequestRepository: FriendRequestRepository)
                                    (implicit ec: ExecutionContext){


  def getAll: Future[Seq[FriendRequest]] = {
    friendRequestRepository.getAll
  }

//  def sendRequest(newRequest: AddFriend) = {
//    val f = FriendRequest(-1,newRequest.senderId,newRequest.recipientId, accepted = false, LocalDateTime.now().withNano(0))
//    friendRequestRepository.getBySenderRecipienId(newRequest).map{
//      case None => friendRequestRepository.sendRequest(f)
//      case Some(_) => throw new FriendRequestException("Request already sent!")
//    }
//
//  }
}
