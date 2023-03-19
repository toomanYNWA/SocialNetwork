package models

import play.api.libs.json.Format
import play.api.libs.json.Json

case class User(
               userId: Option[Long],
               name: String,
               username: String,
               password: String
               )

case class SearchUsers(
                        username: String,
                        userId: Long
                      )

case class SearchUsersResult(
                              userDetails: UserDetails,
                              friend: Boolean
                            )

object User {
  implicit val format: Format[User] = Json.format[User]
}