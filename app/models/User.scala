package models

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