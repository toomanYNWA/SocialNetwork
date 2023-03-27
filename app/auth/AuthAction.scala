package auth

import models.User

import javax.inject.Inject
import play.api.http.HeaderNames
import play.api.mvc.Results.Unauthorized
import play.api.mvc._
import services.UserService

import scala.concurrent.Future.successful
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

case class UserRequest[A](user: User, request: Request[A]) extends WrappedRequest[A](request)
class AuthAction @Inject()(bodyParser: BodyParsers.Default, authService: AuthService, userService: UserService)(implicit ec: ExecutionContext)
  extends ActionBuilder[UserRequest, AnyContent] {

  override def parser: BodyParser[AnyContent] = bodyParser
  override protected def executionContext: ExecutionContext = ec

  private val headerTokenRegex = """Bearer (.+?)""".r


  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result]=
    extractBearerToken(request) map { token =>
      authService.validateJwt(token) match {
        case Success(claim) =>
          userService.getByUsername(claim.subject.head).flatMap
          {
            case Some(userObj) => block(UserRequest(userObj, request))
            case None => Future.successful(Results.Unauthorized("Username or password is wrong"))
          }
        case Failure(t) => Future.successful(Results.Unauthorized(t.getMessage)) // token was invalid - return 401
      }
    } getOrElse Future.successful(Results.Unauthorized) // no token was sent - return 401

  private def extractBearerToken[A](request: Request[A]): Option[String] =
    request.headers.get(HeaderNames.AUTHORIZATION) collect {
      case headerTokenRegex(token) => token
    }
}
