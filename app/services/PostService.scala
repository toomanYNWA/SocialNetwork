package services

import models.{CreatePost, EditPost, Post, User}
import repositories.PostRepository

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PostService @Inject()(postRepository: PostRepository)
                           ( implicit  ec:ExecutionContext) {



  def getAll: Future[Seq[Post]] = {
    postRepository.getAll
  }

  def add(cPost: CreatePost, id: Long) = {
    val p = Post(-1, id, LocalDateTime.now().withNano(0), cPost.text)
    postRepository.insert(p).map(savedPost => savedPost)
  }

  def edit(ePost: EditPost, id: Long) = {
    val p = Post(ePost.postId, id, LocalDateTime.now().withNano(0),ePost.text)
    postRepository.update(p)
  }

  def delete(id: Long) = {
    postRepository.delete(id)
  }

  def getPostsById(id: Long)= {
    postRepository.getPostByUserId(id)
  }
  //dobavi post, flatmap, prebrojati lajkove, flatmap(da li je trenutni user)

}
