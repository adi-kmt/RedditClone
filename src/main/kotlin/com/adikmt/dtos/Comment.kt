package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val postId: String,
    val commentBody: String
)

@Serializable
data class CommentResponse(
    val commentId: String,
    val commentBody: String,
    val commentAuthor: UserResponse,
    val createdAt: String,
    val upvoteNo: Int
)

@Serializable
data class CommentResponseList(
    val commentList: List<CommentResponse>,
    val commentNo: Int
)

@Serializable
data class CommentUpvoteOrDownvote(
    val commentId: String
)