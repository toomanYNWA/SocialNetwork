package models

import play.api.libs.json.{Format, Json}

import java.time.{LocalDate, LocalDateTime}
import java.util.Date

case class Post(
                 postId: Long,
                 userId: Long,
                 postedAt: LocalDateTime,
                 text: String,
               )

case class PostDetails(
                      postId: Long,
                      author: UserDetails,
                      postedAt: LocalDateTime,
                      text: String,
                      liked: Boolean,
                      likeCount: Long
                      )
//case class PostTable(
//                      postId: Option[Long],
//                      userId: Long,
//                      postedAt: Date,
//                      text: String
//                    )

case class CreatePost(
                      text: String,
                      authorId: Long
                     )
//case class PostDto(
//                  authorId: Long,
//                  text: String
//                  )
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
case class Like(
                 likeId: Long,
                 postId: Long,
                 userId: Long,
               )

object Post {

  implicit val format: Format[Post] = Json.format[Post]

  implicit val format2: Format[CreatePost] = Json.format[CreatePost]

  implicit val format3: Format[EditPost] = Json.format[EditPost]
 // implicit def createPostToPost(createPost: CreatePost): Post = Post(createPost.postId, createPost.authorId,  LocalDate.now(), createPost.text)

 // implicit def postToCreatePost(post: Post): CreatePost = CreatePost(post.postId,  post.text, post.userId)
}
object Like {
  implicit val format: Format[Like] = Json.format[Like]

  implicit val format2: Format[LikeUnlikePost] = Json.format[LikeUnlikePost]
}