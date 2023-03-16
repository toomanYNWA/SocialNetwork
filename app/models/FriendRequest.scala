package models

import java.util.Date

case class FriendRequest(
                          friendRequestId: String,
                          senderDetails: UserDetails,
                          sentAt: Date
                        )

case class AddFriend(
                      senderId: String,
                      recipientId: String
                    )

case class FriendRequestResponse(
                                  friendRequestId: String,
                                  accepted: Boolean
                                )