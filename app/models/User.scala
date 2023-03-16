package models

case class User(
               userDetails: UserDetails,
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