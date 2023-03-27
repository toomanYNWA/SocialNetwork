package controllers

import services.PostService
import models.Post.{format2, format3}
import models.{CreatePost, EditPost, Post}
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

import java.time.LocalDate
import javax.inject.Inject
import scala.concurrent.ExecutionContext

class PostController @Inject() (controllerComponents: ControllerComponents, postService: PostService)
                               (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents) {


  def getAll = Action.async { implicit request =>
    postService.getAll.map(res =>
      Ok(Json.toJson(res)))
  }

  def add = Action.async(parse.json[CreatePost]) { implicit request =>
      val newPost = request.body
      val p = CreatePost(newPost.text,newPost.authorId)
      postService
        .add(p)
        .map(res => Ok(Json.toJson(res)))
  }

  def update = Action.async(parse.json[EditPost]){ implicit request =>
      val editPost = request.body
    postService
      .edit(editPost)
      .map(res => Ok(Json.toJson(res)))
  }

  def deletePost(id: Long) = Action.async { implicit request =>
    postService.delete(id).map(res =>
      Ok(Json.toJson(res))
    )
  }
}