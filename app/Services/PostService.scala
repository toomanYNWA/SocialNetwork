package Services

import models.{CreatePost, Post, User}
import repositories.PostRepository

import java.time.{LocalDate, LocalDateTime}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class PostService @Inject()(postRepository: PostRepository)
                           ( implicit  ec:ExecutionContext) {

  def getAll: Future[Seq[Post]] = {
    postRepository.getAll
  }

  def add(cPost: CreatePost) = {
    val p = Post(-1, cPost.authorId, LocalDateTime.now().withNano(0), cPost.text)
    postRepository.insert(p).map(savedPost => savedPost)
  }

}
