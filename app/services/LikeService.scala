package services

import models.{Like, LikeUnlikePost}
import repositories.LikeRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

class LikeService @Inject()(likeRepository: LikeRepository)
                           ( implicit  ec:ExecutionContext) {


  def add(like: LikeUnlikePost): Future[Unit] = {
    val l = Like(-1,like.postId, like.userId)
    likeRepository.getLike(l).flatMap{
      case None => likeRepository.add(l)
      case Some(_) =>  likeRepository.delete(l)
    }
  }

  def getAll() = {
    likeRepository.getAll()
  }
}
