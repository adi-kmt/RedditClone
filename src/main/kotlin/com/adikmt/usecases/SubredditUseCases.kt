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
    suspend fun add(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse?>
}

fun interface GetSubredditByNameUsecase {
    suspend fun get(subredditName: SubredditName): Result<SubredditResponse?>
}

fun interface SearchSubredditByNameUsecase {
    suspend fun get(subredditName: SubredditName): Result<SubredditResponseList>
}

fun interface FollowSubredditUsecase {
    suspend fun follow(userName: UserName, subredditName: SubredditName): Result<SubredditName>
}

fun interface UnFollowSubredditUsecase {
    suspend fun unFollow(userName: UserName, subredditName: SubredditName): Result<SubredditName>
}

fun interface GetAllSubredditsFollowedUsecase {
    suspend fun get(userName: UserName): Result<SubredditResponseList>
}

fun addSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    AddSubredditUsecase { userName: UserName, subredditRequest: SubredditRequest ->
        withContext(dispatcher) {
            subredditService.addSubreddit(userName, subredditRequest)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditResponse>(e)
}

fun getSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    GetSubredditByNameUsecase { subredditName: SubredditName ->
        withContext(dispatcher) {
            subredditService.getSubredditByName(subredditName)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditResponse>(e)
}

fun searchSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    SearchSubredditByNameUsecase { subredditName: SubredditName ->
        withContext(dispatcher) {
            subredditService.searchSubredditByName(subredditName)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditResponseList>(e)
}

fun followSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    FollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
        withContext(dispatcher) {
            subredditService.followSubreddit(userName, subredditName)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditName>(e)
}

fun unFollowSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    UnFollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
        withContext(dispatcher) {
            subredditService.unFollowSubreddit(userName, subredditName)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditName>(e)
}

fun getAllSubredditsFollowedUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = try {
    GetAllSubredditsFollowedUsecase { userName: UserName ->
        withContext(dispatcher) {
            subredditService.getAllSubredditsFollowed(userName)
        }
    }
} catch (e: Exception) {
    Result.failure<SubredditResponseList>(e)
}