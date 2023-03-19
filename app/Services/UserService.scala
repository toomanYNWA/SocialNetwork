package Services

import models.User
import repositories.UserRepository
import slick.lifted.Functions.user

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository : UserRepository)
                           (implicit ec:ExecutionContext){


  def getAll: Future[Seq[User]] = {
    userRepository.getAll
  }

  def add(user: User) = {
    userRepository.add(user)
  }
}
