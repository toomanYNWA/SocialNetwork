package controllers

import Services.UserService
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import com.google.inject.Inject
import models.User
import models.exception.RegisterUserException
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.twirl.api.Html

import scala.concurrent.{ExecutionContext, Future}
import scala.xml.Text

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


}


