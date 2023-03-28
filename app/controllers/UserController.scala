package controllers

import auth.AuthAction
import services.UserService
import com.google.inject.Inject
import models.{EditUserDto, LoggedUser, User}
import models.exception.RegisterUserException
import play.api.mvc.{AbstractController, ControllerComponents}
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

  def add = Action.async(parse.json[User]) { implicit request =>
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

  def searchUsers(text: String) = Action.async { implicit  request =>
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


}


