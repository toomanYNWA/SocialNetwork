package controllers

import Services.UserService
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import com.google.inject.Inject
import models.User
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json.{JsError, JsSuccess, Json}
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

  def add = Action.async(parse.json) {

    implicit request =>
      val newUser = request.body.validate[User]
      newUser match {
        case JsSuccess(userObj, _) =>
          userService.add(userObj).map(res =>
            Ok(res)
          )
        case JsError(errors) => Future.successful(BadRequest(errors.toString))
      }
  }
  //def list = Action.async {
  //      userRepository.all().map { case (stocks) => Ok(views.html.list(stocks)) }
  //}
}


