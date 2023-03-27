package models

import java.util.Date

case class FRequestDetails(
                          friendRequestId: Long,
                          senderDetails: UserDetails,
                          sentAt: Date
                        )

case class AddFriend(
                      senderId: Long,
                      recipientId: Long
                    )

case class FriendRequestResponse(
                                  friendRequestId: Long,
                                  accepted: Boolean
                                )
FriendRequest
case class FriendRequest(
                        friendRequestId: Long,
                        senderId: Long,
                        recipientId: Long,
                        accepted: Boolean
                        )