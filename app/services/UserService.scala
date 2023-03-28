package services

import auth.JwtUtil
import models.exception.{LoginException, RegisterUserException}
import models._
import org.mindrot.jbcrypt.BCrypt
import repositories.UserRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository : UserRepository)
                           (implicit ec:ExecutionContext){



  def getAll: Future[Seq[User]] = {
    userRepository.getAll
  }

  def add(user: User): Future[User] = { // ogranicenja: da username nije zauzet, da mora biti 5-20 karaktera  i sifra min 5 karkter
    userRepository.getByUsername(user.username).flatMap {
      case None =>
        val hashedPassword = BCrypt.hashpw(user.password,BCrypt.gensalt())
        val updatedUser = user.copy(password = hashedPassword)
        userRepository.add(updatedUser)
      case Some(_) => throw new RegisterUserException("Username already exists!")
    }
  }

  def getByUsername(username: String): Future[Option[User]] = {
    userRepository.getByUsername(username)
  }


  def getProfileByUsername(username: String): Future[Option[Profile]] = {
    userRepository.getByUsername(username).map { maybeUser =>
      maybeUser.map { user =>
        Profile(
          userDetails = UserDetails(user.userId.get, user.username, user.name),
          posts = List.empty
        )
      }
    }
  }

  def updateUser(loggedUser: User, editParms: EditUserDto): Future[User] ={
    userRepository.getByUsername(editParms.username).flatMap {
      case Some(_) =>
        throw new Exception("custom username already taken")
      case _ =>
        val updatedUser = loggedUser.copy(
          username = editParms.username,
          name = editParms.name
        )
        userRepository.update(updatedUser)
    }
  }

  def searchUsers(text: String) = {
    userRepository.searchByUsernameOrName(text)
  }

  def login(loggedUser: LoggedUser):Future[String] = {
    userRepository.getByUsername(loggedUser.username).map {
      case None => throw new LoginException("Username doesn't exist")
      case Some(userObj) =>
        if (BCrypt.checkpw(loggedUser.password, userObj.password)) {
          val jwt = JwtUtil.createToken(loggedUser)
          jwt
        } else
          throw new LoginException("Wrong password")
    }
  }
}
