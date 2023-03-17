package controllers

import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import com.google.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import play.twirl.api.Html

import scala.concurrent.{ExecutionContext, Future}
import scala.xml.Text

class UserController @Inject() (controllerComponents: ControllerComponents)
                               (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)
{
        def worD = Action.async { implicit request =>
        Future.successful(Ok("Hello")) }

        //def list = Action.async {
        //      userRepository.all().map { case (stocks) => Ok(views.html.list(stocks)) }
        //}
}


