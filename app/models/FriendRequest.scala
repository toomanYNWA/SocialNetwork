package models

import play.api.libs.json.{Format, Json}

import java.time.LocalDateTime
import java.util.Date

case class FRequestDetails(
                          friendRequestId: Long,
                          senderDetails: UserDetails,
                          sentAt: LocalDateTime
                        )

case class AddFriend(
                      senderId: Long,
                      recipientId: Long
                    )

case class FriendRequestResponse(
                                  friendRequestId: Long,
                                  accepted: Boolean
                                )

case class FriendRequest(
                        friendRequestId: Long,
                        senderId: Long,
                        recipientId: Long,
                        accepted: Boolean,
                        sentAt: LocalDateTime
                        )

object FriendRequest{
  implicit val format: Format[FriendRequest] = Json.format[FriendRequest]

}

object AddFriend{
  implicit val format: Format[AddFriend] = Json.format[AddFriend]
}

object FriendRequestResponse{
  implicit val format: Format[FriendRequestResponse] = Json.format[FriendRequestResponse]
}