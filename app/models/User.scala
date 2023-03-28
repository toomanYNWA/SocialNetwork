package models

import play.api.libs.json.Format
import play.api.libs.json.Json

case class User(
               userId: Option[Long],
               name: String,
               username: String,
               password: String
               )

case class EditUserDto(
                   name: String,
                   username: String
                   )

case class SearchUsers(
                        text: String,
                        userId: Long
                      )

case class SearchUsersResult(
                              userDetails: UserDetails,
                              friend: Boolean
                            )

case class LoggedUser(
                      username: String,
                      password: String
                     )

object User {
  implicit val format: Format[User] = Json.format[User]
}

object LoggedUser{
  implicit val format: Format[LoggedUser] = Json.format[LoggedUser]
}

object EditUserDto {
  implicit val format = Json.format[EditUserDto]
}