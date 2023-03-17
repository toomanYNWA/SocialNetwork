package models

import java.util.Date

case class Post(
                 postId: Option[Long],
                 authorDetails: UserDetails,
                 likeCount: Long,
                 liked: Boolean,
                 postedAt: Date,
                 text: String
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