package models

import java.util.Date

case class Post(
                 postId: String,
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
                     postId: String,
                     authorId: String
                   )

case class DeletePost(
                       postId: String,
                       authorId: String
                     )

case class LikeUnlikePost(
                           postId: String,
                           userId: String,
                         )