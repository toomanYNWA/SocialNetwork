package models

import play.api.libs.json.Format
import play.api.libs.json.Json

case class User(
               userId:  Long,
               name: String,
               username: String,
               password: String
               )
case class UserDto(
                    name: String,
                    username: String,
                    password: String
                  )

case class EditUserDto(
                   name: String
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

case class PasswordChange(
                         password: String,
                         newPassword: String
                         )
case class LoggedUserId(
                 userId: Long
)
case class FriendId(
                   userId: Long
                   )

object User {
  implicit val format: Format[User] = Json.format[User]
}
object UserDto {
  implicit val format: Format[UserDto] = Json.format[UserDto]
}

object LoggedUser{
  implicit val format: Format[LoggedUser] = Json.format[LoggedUser]
}

object EditUserDto {
  implicit val format = Json.format[EditUserDto]
}

object PasswordChange{
  implicit val format: Format[PasswordChange] = Json.format[PasswordChange]
}
object LoggedUserId{
  implicit val format: Format[LoggedUserId] = Json.format[LoggedUserId]
}

object FriendId{
  implicit val format: Format[FriendId] = Json.format[FriendId]
}