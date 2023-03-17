package models

import java.util.Date


case class Profile(
                    userDetails: UserDetails,
                    posts: List[Post]
                  )

case class UserDetails(
                        userId: Long,
                        username: String,
                        name: String
                      )




