package com.adikmt.services

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.repositories.SubredditRepository

interface SubredditService {
    suspend fun addSubreddit(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse>

    suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?>

    suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList>

    suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    suspend fun unFollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    suspend fun getAllSubredditsFollowed(userName: UserName): Result<SubredditResponseList>
}

class SubredditServiceImpl(private val subredditRepository: SubredditRepository) : SubredditService {
    override suspend fun addSubreddit(
        userName: UserName,
        subredditRequest: SubredditRequest
    ): Result<SubredditResponse> =
        subredditRepository.addSubreddit(userName, subredditRequest)

    override suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?> =
        subredditRepository.getSubredditByName(subredditName)

    override suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList> =
        subredditRepository.searchSubredditByName(subredditName)

    override suspend fun followSubreddit(
        userName: UserName,
        subredditName: SubredditName
    ): Result<SubredditName> =
        subredditRepository.followSubreddit(userName, subredditName)

    override suspend fun unFollowSubreddit(
        userName: UserName,
        subredditName: SubredditName
    ): Result<SubredditName> =
        subredditRepository.unfollowSubreddit(userName, subredditName)

    override suspend fun getAllSubredditsFollowed(userName: UserName): Result<SubredditResponseList> =
        subredditRepository.getAllFollowedSubreddits(userName)
}