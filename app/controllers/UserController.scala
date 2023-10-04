package controllers

import auth.AuthAction
import services.UserService
import com.google.inject.Inject
import models.{EditUserDto, FriendId, LoggedUser, LoggedUserId, PasswordChange, User, UserDto}
import models.exception.{FriendProfileException, RegisterUserException}
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.concurrent.{ExecutionContext, Future}


class UserController @Inject()(controllerComponents: ControllerComponents, userService: UserService, authAction: AuthAction)
                              (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {
  def worD = Action.async { implicit request =>
    Future.successful(Ok("Hello"))
  }

  def getAll = Action.async { implicit request =>
    userService.getAll.map(res =>
      Ok(Json.toJson(res)))
  }
//add ne radi -- pogledaj kasnije
  def add: Action[UserDto] = Action.async(parse.json[UserDto]) { implicit request =>
      val newUser = request.body
      userService
        .add(newUser)
        .map(res => Ok(Json.toJson(res)))
        .recover {
          case ex: RegisterUserException =>
            BadRequest(Json.obj("message" -> ex.getMessage))
        }
  }

  def updateUser = authAction.async(parse.json[EditUserDto]) { implicit request =>
        userService.updateUser(request.user, request.body).map(res =>
          Ok(Json.toJson(res))
        )
  }

  def searchUsers(text: String) = authAction.async { implicit  request =>
    //preko tokena dobiti userId
    //request.headers.get("token")
    userService.searchUsers(text).map(res =>
          Ok(Json.toJson(res)))

  }

  def loginUser  = Action.async(parse.json[LoggedUser]) { implicit request =>
    val loggedUser = request.body
    userService
      .login(loggedUser)
      .map(res => Created(Json.toJson(res)))
  }

  def changePassword = authAction.async(parse.json[PasswordChange]) { implicit request =>
    val changeInfo = request.body
    userService
      .changePassword(changeInfo, request.user)
      .map(res => Ok("Password changed successfully"))
  }

  def viewMyProfile = authAction.async{ implicit request =>
    val id = request.user.userId
    userService
      .getProfile(id)
      .map(res => Ok(Json.toJson(res)))
  }

  def viewFriendProfile  = authAction.async(parse.json[FriendId]){ implicit request =>
    userService
      .getFriendInfo(request.body.userId, request.user)
      .map(res => Ok(Json.toJson(res)))
      .recover{
        case ex: FriendProfileException =>
          NotFound(Json.obj("message" -> ex.getMessage))
      }
  }

}


