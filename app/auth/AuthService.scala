package auth

import models.exception.JwtValidationException

import javax.inject.Inject
import pdi.jwt.{JwtAlgorithm, JwtBase64, JwtClaim, JwtJson}
import play.api.Configuration

import scala.util.{Failure, Success, Try}

class AuthService  @Inject()(config: Configuration){

//  // A regex that defines the JWT pattern and allows us to
//  // extract the header, claims and signature
//  private val jwtRegex = """(.+?)\.(.+?)\.(.+?)""".r
//
//  // Your Auth0 domain, read from configuration
//  private def domain = config.get[String]("auth0.domain")
//
//  // Your Auth0 audience, read from configuration
//  private def audience = config.get[String]("auth0.audience")
//
//  // The issuer of the token. For Auth0, this is just your Auth0
//  // domain including the URI scheme and a trailing slash.
//  private def issuer = s"https://$domain/"

  def validateJwt(token: String): Try[JwtClaim] = for {
    claims <- JwtJson.decode(token, "secretKey", Seq(JwtAlgorithm.HS256)) // Decode the token using the secret key

    _ <- validateClaims(claims, token) // validate the data stored inside the token
  } yield claims

  // Validates the claims inside the token. 'isValid' checks the issuedAt, expiresAt,
  // issuer and audience fields.

  private val validateClaims = (claims: JwtClaim, token: String) =>
    if (JwtUtil.isValidToken(token)) {
      Success(claims)
    } else {
      Failure(new JwtValidationException("The JWT did not pass validation"))
    }
}
