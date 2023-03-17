package models

import java.util.Date

case class FriendRequest(
                          friendRequestId: Option[Long],
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