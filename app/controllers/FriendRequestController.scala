package controllers

import models.{AddFriend, FriendRequestResponse}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.FriendRequestService

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class FriendRequestController @Inject() (controllerComponents: ControllerComponents,friendRequestService: FriendRequestService)
                                        (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)  {

  def getAll = Action.async { implicit request =>
    friendRequestService.getAll.map(res =>
    Ok(Json.toJson(res)))
  }

//  def sendRequest = Action.async(parse.json[AddFriend]) { implicit request =>
//    val newRequest = request.body
//    friendRequestService
//      .sendRequest(newRequest)
//      .map(res => Ok(Json.toJson(res)))
//  }

//  def answerFR = Action.async(parse.json[FriendRequestResponse]){ implicit request =>
//    val response = request.body
//    friendRequestService
//      .answerFR(response)
//      .map(res => Ok(Json.toJson(res)))
//  }
}
