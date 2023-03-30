package controllers

import auth.AuthAction
import services.PostService
import models.Post.{format2, format3}
import models.{CreatePost, EditPost, Post}
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class PostController @Inject() (controllerComponents: ControllerComponents, postService: PostService,authAction: AuthAction)
                               (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {


  def getAll = Action.async { implicit request =>
    postService.getAll.map(res =>
      Ok(Json.toJson(res)))
  }

  def add = authAction.async(parse.json[CreatePost]) { implicit request =>
    val cPost = request.body
      postService
        .add(cPost,request.user.userId)
        .map(res => Ok(Json.toJson(res)))
  }

  def update = authAction.async(parse.json[EditPost]){ implicit request =>
      val editPost = request.body
    postService
      .edit(editPost, request.user.userId)
      .map(res => Ok(Json.toJson(res)))
  }

  def deletePost(id: Long) = authAction.async { implicit request =>
    postService.delete(id).map(res =>
      Ok(Json.toJson(res))
    )
  }
}
