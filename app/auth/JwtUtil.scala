package auth



import models.LoggedUser
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader}
import pdi.jwt.algorithms.JwtHmacAlgorithm
import repositories.UserRepository

import java.time.Instant
import java.util.Date
import javax.inject.Inject
import scala.util.Try

class JwtUtil @Inject()(userRepository: UserRepository)  {

}

object JwtUtil {
  def createToken(loggedUser: LoggedUser): String = {
    val algorithm: JwtHmacAlgorithm = JwtAlgorithm.HS256
    val header = JwtHeader(algorithm, "JWT")
    val claims = JwtClaim(subject = Some(loggedUser.username), expiration = Some(Date.from(Instant.now().plusSeconds(36000)).getTime))
    val key: String = "secretKey"

    Jwt.encode(header, claims, key)
  }

  def isValidToken(jwtToken: String): Boolean = {
    Jwt.isValid(jwtToken, "secretKey", Seq(JwtAlgorithm.HS256))
  }

  def decodeToken(jwtToken: String): Try[JwtClaim] = {
    Jwt.decode(jwtToken, "secretKey", Seq(JwtAlgorithm.HS256))
  }
}