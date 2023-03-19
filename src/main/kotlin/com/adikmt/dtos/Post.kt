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
    val postId: String,
    val subredditName: String,
    val postHeading: String,
    val postBody: String,
    val postAuthor: UserResponse,
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