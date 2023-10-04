package controllers

import auth.AuthAction
import models.exception.FriendRequestException
import models.{AddFriend, FriendRequestResponse, PasswordChange}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.FriendRequestService

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class FriendRequestController @Inject() (controllerComponents: ControllerComponents, authAction: AuthAction,friendRequestService: FriendRequestService)
                                        (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)  {

  def getAll = Action.async { implicit request =>
    friendRequestService.getAll.map(res =>
    Ok(Json.toJson(res)))
  }

  def sendRequest = authAction.async(parse.json[AddFriend]) { implicit request =>
    friendRequestService
      .sendRequest(request.body, request.user)
      .map(res => Ok(Json.toJson(res)))
      .recover {
        case ex: FriendRequestException =>
          BadRequest(Json.obj("message" -> ex.getMessage))
      }
  }

  def answerFR = authAction.async(parse.json[FriendRequestResponse]) { implicit request =>
    val response = request.body
    friendRequestService
      .answerFR(response)
      .map(res => Ok("Answered!"))
      .recover {
        case ex: FriendRequestException =>
          BadRequest(Json.obj("message" -> ex.getMessage))
      }
  }

  def getMyFR = authAction.async{ implicit request =>
    friendRequestService
      .getMyFR(request.user)
      .map(res => Ok(Json.toJson(res)))
  }

  def deleteFR(id: Long) = Action.async { implicit request =>
    friendRequestService
      .deleteFR(id)
      .map(res => Ok("Request Deleted!"))
  }

}
