package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class SubredditRequest(
    val subredditName: String,
    val subredditDesc: String
)

@Serializable
data class SubredditResponse(
    val id: Long,
    val subredditName: String,
    val subredditDesc: String?,
    val createdByUser: UserName
)

@Serializable
data class SubredditResponseList(
    val subredditList: List<SubredditResponse>,
    val subredditNo: Int,
    val limit: Int,
    val offset: Long
)

@JvmInline
@Serializable
value class SubredditName(val value: String)