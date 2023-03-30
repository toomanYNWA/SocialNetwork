package models

import play.api.libs.json.{Format, Json}
import UserDetails.format2
import java.util.Date
import scala.concurrent.Future


case class Profile(
                    userDetails: UserDetails,
                    posts: Seq[Post]
                  )

case class UserDetails(
                        userId: Long,
                        username: String,
                        name: String
                      )
object UserDetails{
  implicit val format2: Format[UserDetails] = Json.format[UserDetails]
}
object Profile{
  implicit val format: Format[Profile] = Json.format[Profile]
}