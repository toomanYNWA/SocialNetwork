package controllers

import auth.AuthAction
import com.google.inject.Inject
import models.Like.format2
import models.LikeUnlikePost
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.LikeService

import scala.concurrent.ExecutionContext

class LikeController @Inject()(controllerComponents: ControllerComponents, likeService: LikeService,authAction: AuthAction)
                              (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents){

  def addOrRemove = authAction.async (parse.json[LikeUnlikePost]){ implicit request =>
    val likeUnlikePost = request.body
    likeService
      .add(likeUnlikePost, request.user.userId)
      .map(res => Ok("Like added/removed"))
  }

  def getAll = Action.async { implicit request =>
    likeService.getAll.map(res =>
      Ok(Json.toJson(res)))
  }
}
