package controllers

import services.UserService
import com.google.inject.Inject
import models.{LoggedUser, User}
import models.exception.RegisterUserException
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.{JsError, JsSuccess, Json}
import scala.concurrent.{ExecutionContext, Future}


class UserController @Inject()(controllerComponents: ControllerComponents, userService: UserService)
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

  def updateUser = Action.async(parse.json) { implicit request =>
    val newUser = request.body.validate[User]
    newUser match {
      case JsSuccess(userObj, _) =>
        userService.updateUser(userObj).map(res =>
          Ok(Json.toJson(res))
        )
      case JsError(errors) => Future.successful(BadRequest(errors.toString))
    }
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


