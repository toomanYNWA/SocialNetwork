package models

import java.time.LocalDate
import java.util.Date

case class Post(
                 postId: Option[Long],
                 userId: Long,
                 username: String,
                 name: String,
                 postedAt: LocalDate,
                 text: String,
                 liked: Boolean,
                 likeCount: Long
               )

case class CreatePost(
                       text: String,
                       authorId: String
                     )

case class EditPost(
                     text: String,
                     postId: Long,
                     authorId: Long
                   )

case class DeletePost(
                       postId: Long,
                       authorId: String
                     )

case class LikeUnlikePost(
                           postId: Long,
                           userId: Long,
                         )