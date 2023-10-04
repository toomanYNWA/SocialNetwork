package services

import auth.JwtUtil
import models.exception.{ChangePasswordException, FriendProfileException, FriendRequestException, LoginException, RegisterUserException}
import models._
import org.mindrot.jbcrypt.BCrypt
import play.api.mvc.Results.NotFound
import repositories.UserRepository

import scala.concurrent.Await
import scala.concurrent.duration._
import javax.inject.Inject
import scala.collection.Searching
import scala.collection.Searching.Found
import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository : UserRepository,postService: PostService, friendRequestService: FriendRequestService)
                           (implicit ec:ExecutionContext){



  def getAll: Future[Seq[User]] = {
    userRepository.getAll
  }

  def add(user: UserDto): Future[User] = { // ogranicenja: da username nije zauzet, da mora biti 5-20 karaktera  i sifra min 5 karkter
    userRepository.getByUsername(user.username).flatMap {
      case None =>
       // val hashedPassword = BCrypt.hashpw(user.password,BCrypt.gensalt())
        //val updatedUser = user.copy(password = hashedPassword)
        val newUser = User(-1,user.name,user.username,user.password)
        userRepository.add(newUser)
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
          userDetails = UserDetails(user.userId, user.username, user.name),
          posts = List.empty
        )
      }
    }
  }

  def updateUser(loggedUser: User, editParms: EditUserDto): Future[User] ={
        val updatedUser = loggedUser.copy(
          name = editParms.name
        )
        userRepository.update(updatedUser)
  }

  def searchUsers(text: String) = {
    userRepository.searchByUsernameOrName(text)
  }

  def login(loggedUser: LoggedUser):Future[String] = {
    userRepository.getByUsername(loggedUser.username).map {
      case None => throw new LoginException("Username doesn't exist")
      case Some(userObj) =>
        if (loggedUser.password == userObj.password) {
          val jwt = JwtUtil.createToken(userObj)
          jwt
        } else
          throw new LoginException("Wrong password")
    }
  }

  def changePassword(changeInfo: PasswordChange,user:User):Future[User] = {
    if(changeInfo.password != user.password){
      throw new ChangePasswordException("Wrong password!")
    }
    userRepository.getById(user.userId).flatMap {
      case None => throw new ChangePasswordException("No such user")
      case Some(userToChange) =>
        userRepository.updatePassword(userToChange,changeInfo.newPassword)
    }
  }

  def getProfile(id: Long) = {
    userRepository.getById(id).flatMap {
      case None => throw new Exception("No such user")
      case Some(userObj) => val userDetails = UserDetails(id, userObj.username, userObj.name)
        postService.getPostsById(id).map{ posts =>
          val profile = Profile(userDetails, posts)
          profile
        }

    }

  }

  def getFriendInfo(id: Long, user: User): Future[Profile] = {
    userRepository.getById(id).flatMap {
      case None => throw new FriendProfileException("No such user!")
      case Some(obj) =>
        friendRequestService.getSpecialFR(obj.userId, user.userId).flatMap {
          case None => throw new FriendProfileException("Not friends!")
          case Some(fro) =>
            if (fro.accepted) {
              val profile = getProfile(id)
              profile
            } else {
              throw new FriendProfileException("Friend Request Pending!")
            }
        }
    }
  }
}
