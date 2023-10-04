package services

import models.exception.DeletePostException
import models.{CreatePost, EditPost, Post, PostDetails, User}
import repositories.PostRepository

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PostService @Inject()(postRepository: PostRepository, friendRequestService: FriendRequestService)
                           ( implicit  ec:ExecutionContext) {



  def getAll: Future[Seq[Post]] = {
    postRepository.getAll
  }

  def add(cPost: CreatePost, id: Long) = {
    val p = Post(-1, id, LocalDateTime.now().withNano(0), cPost.text)
    postRepository.insert(p).map(savedPost => savedPost)
  }

  def edit(ePost: EditPost, id: Long) = {
//    postRepository.getPostsByUserId(id).map{  =>
//      case ePost.postId
//    }
    val p = Post(ePost.postId, id, LocalDateTime.now().withNano(0),ePost.text)
    postRepository.update(p)
  }

  def delete(id: Long,userId: Long) = {
    postRepository.getPostsByUserIdAndId(id, userId).flatMap{
      case None => throw new DeletePostException("Can't delete that post")
      case Some(obj) =>
        postRepository.delete(obj.postId)
    }

  }

  def getPostsById(id: Long)= {
    postRepository.getPostsByUserId(id)
  }


//  def getFriendsPosts(user: User) = {
//  }
}
