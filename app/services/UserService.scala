package services

import auth.JwtUtil
import models.{LoggedUser, User}
import models.exception.{LoginException, RegisterUserException}
import repositories.UserRepository
import slick.lifted.Functions.user
import org.mindrot.jbcrypt.BCrypt

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

  def getByUsername(username: String): Future[Option[User]] = {
    userRepository.getByUsername(username)
  }

  def updateUser(user: User) ={
    userRepository.update(user)
  }

  def searchUsers(text: String) = {
    userRepository.searchByUsernameOrName(text)
  }

  def login(loggedUser: LoggedUser): Future[String] = {
    userRepository.getByUsername(loggedUser.username).flatMap {
      case None => throw new LoginException("Username doesn't exist")
      case Some(userObj) =>
        if (BCrypt.checkpw(loggedUser.password, userObj.password)) {
          userRepository.login(loggedUser)
        } else
          throw new LoginException("Wrong password")
    }
  }
}
