package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class SubredditRequest(
    val subredditName: String,
    val subredditDesc: String
)

@Serializable
data class SubredditResponse(
    val subredditName: String,
    val subredditDesc: String,
    val createdAt: String,
    val createdByUser: UserResponse
)

@Serializable
data class SubredditResponseList(
    val subredditList: List<SubredditResponse>,
    val subredditNo: Int
)

@JvmInline
@Serializable
value class SubredditName(val value: String)