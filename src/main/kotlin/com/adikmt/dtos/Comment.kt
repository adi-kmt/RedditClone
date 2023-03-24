package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val postId: String,
    val commentBody: String,
    val parentComment: Long
)

@Serializable
data class CommentResponse(
    val commentId: Long?,
    val commentBody: String,
    val commentAuthor: UserName,
    val parentComment: Long?,
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

@JvmInline
@Serializable
value class CommentId(val value: String) {

    companion object {
        fun toLong(commentId: CommentId): Long = commentId.value.toLong()
    }
}