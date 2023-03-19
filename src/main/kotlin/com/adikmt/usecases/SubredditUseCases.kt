package com.adikmt.usecases

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.services.SubredditService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun interface AddSubredditUsecase {
    suspend fun add(subredditRequest: SubredditRequest): Result<SubredditResponse>
}

fun interface GetSubredditByNameUsecase {
    suspend fun get(subredditName: SubredditName): Result<SubredditResponse>
}

fun interface SearchSubredditByNameUsecase {
    suspend fun get(subredditName: SubredditName): Result<SubredditResponseList>
}

fun interface FollowSubredditUsecase {
    suspend fun follow(userName: UserName, subredditName: SubredditName): Result<SubredditResponseList>
}

fun interface UnFollowSubredditUsecase {
    suspend fun unFollow(userName: UserName, subredditName: SubredditName): Result<SubredditResponseList>
}

fun interface GetAllSubredditsFollowedUsecase {
    suspend fun get(userName: UserName): Result<SubredditResponseList>
}

fun addSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = AddSubredditUsecase { subredditRequest: SubredditRequest ->
    withContext(dispatcher) {
        subredditService.addSubreddit(subredditRequest)
    }
}

fun getSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = GetSubredditByNameUsecase { subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.getSubredditByName(subredditName)
    }
}

fun searchSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = SearchSubredditByNameUsecase { subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.searchSubredditByName(subredditName)
    }
}

fun followSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = FollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.followSubreddit(userName, subredditName)
    }
}

fun unFollowSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = UnFollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.unFollowSubreddit(userName, subredditName)
    }
}

fun getAllSubredditsFollowedUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = GetAllSubredditsFollowedUsecase { userName: UserName ->
    withContext(dispatcher) {
        subredditService.getAllSubredditsFollowed(userName)
    }
}