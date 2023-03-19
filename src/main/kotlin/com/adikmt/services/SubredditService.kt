package com.adikmt.services

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName

interface SubredditService {
    suspend fun addSubreddit(subredditRequest: SubredditRequest): Result<SubredditResponse>

    suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse>

    suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList>

    suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditResponseList>

    suspend fun unFollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditResponseList>

    suspend fun getAllSubredditsFollowed(userName: UserName): Result<SubredditResponseList>
}

class SubredditServiceImpl : SubredditService {
    override suspend fun addSubreddit(subredditRequest: SubredditRequest): Result<SubredditResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun followSubreddit(
        userName: UserName,
        subredditName: SubredditName
    ): Result<SubredditResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun unFollowSubreddit(
        userName: UserName,
        subredditName: SubredditName
    ): Result<SubredditResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSubredditsFollowed(userName: UserName): Result<SubredditResponseList> {
        TODO("Not yet implemented")
    }
}