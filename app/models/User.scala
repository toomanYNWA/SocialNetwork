package models

case class User(
               userId: String,
               name: String,
               username: String,
               password: String
               )

case class SearchUsers(
                        username: String,
                        userId: String
                      )

case class SearchUsersResult(
                              userDetails: UserDetails,
                              friend: Boolean
                            )