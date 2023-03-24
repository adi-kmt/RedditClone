package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val subredditName: String,
    val postHeading: String,
    val postBody: String,
)

@Serializable
data class PostResponse(
    val postId: Long?,
    val subredditName: String,
    val postHeading: String,
    val postBody: String,
    val postAuthor: UserName,
    val postCreatedAt: String,
    val noOfUpvotes: Int
)

@Serializable
data class PostUpvoteOrDownvote(
    val postId: String
)

@Serializable
data class PostResponseList(
    val postList: List<PostResponse>,
    val postNo: Int
)

@JvmInline
@Serializable
value class PostHeading(val value: String)

@JvmInline
@Serializable
value class PostId(val value: String) {
    companion object {
        fun toLong(postId: PostId): Long = postId.value.toLong()
    }
}