package Services

import models.User
import models.exception.RegisterUserException
import repositories.UserRepository
import slick.lifted.Functions.user

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository : UserRepository)
                           (implicit ec:ExecutionContext){


  def getAll: Future[Seq[User]] = {
    userRepository.getAll
  }

  def add(user: User): Future[User] = { // ogranicenja: da username nije zauzet, da mora biti 5-20 karaktera  i sifra min 5 karkter
    userRepository.getByUsername(user.username).flatMap {
      case None => userRepository.add(user)
      case Some(_) => throw new RegisterUserException("Username already exists!")
    }
  }


  def updateUser(user: User) ={
    userRepository.update(user)
  }

  def searchUsers(text: String) = {
    userRepository.searchByUsernameOrName(text)
  }
}
