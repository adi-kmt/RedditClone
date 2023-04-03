package com.adikmt.usecases

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.services.SubredditService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/** Add subreddit usecase */
fun interface AddSubredditUsecase {
    /**
     * Add
     *
     * @param userName
     * @param subredditRequest
     * @return
     */
    suspend fun add(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse?>
}

/**
 * Get subreddit by name usecase
 *
 * @constructor Create empty Get subreddit by name usecase
 */
fun interface GetSubredditByNameUsecase {
    /**
     * Get
     *
     * @param subredditName
     * @return
     */
    suspend fun get(subredditName: SubredditName): Result<SubredditResponse?>
}

/**
 * Search subreddit by name usecase
 *
 * @constructor Create empty Search subreddit by name usecase
 */
fun interface SearchSubredditByNameUsecase {
    /**
     * Get
     *
     * @param subredditName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun get(subredditName: SubredditName, limit: Int, offset: Long): Result<SubredditResponseList>
}

/**
 * Follow subreddit usecase
 *
 * @constructor Create empty Follow subreddit usecase
 */
fun interface FollowSubredditUsecase {
    /**
     * Follow
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun follow(userName: UserName, subredditName: SubredditName): Result<SubredditName>
}

/**
 * Un follow subreddit usecase
 *
 * @constructor Create empty Un follow subreddit usecase
 */
fun interface UnFollowSubredditUsecase {
    /**
     * Un follow
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun unFollow(userName: UserName, subredditName: SubredditName): Result<SubredditName>
}

/**
 * Get all subreddits followed usecase
 *
 * @constructor Create empty Get all subreddits followed usecase
 */
fun interface GetAllSubredditsFollowedUsecase {
    /**
     * Get
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun get(userName: UserName, limit: Int, offset: Long): Result<SubredditResponseList>
}

/**
 * Add subreddit usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun addSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = AddSubredditUsecase { userName: UserName, subredditRequest: SubredditRequest ->
    withContext(dispatcher) {
        subredditService.addSubreddit(userName, subredditRequest)
    }
}

/**
 * Get subreddit by name usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun getSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = GetSubredditByNameUsecase { subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.getSubredditByName(subredditName)
    }
}

/**
 * Search subreddit by name usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun searchSubredditByNameUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = SearchSubredditByNameUsecase { subredditName: SubredditName, limit: Int, offset: Long ->
    withContext(dispatcher) {
        subredditService.searchSubredditByName(subredditName, limit, offset)
    }
}

/**
 * Follow subreddit usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun followSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = FollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.followSubreddit(userName, subredditName)
    }
}

/**
 * Un follow subreddit usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun unFollowSubredditUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = UnFollowSubredditUsecase { userName: UserName, subredditName: SubredditName ->
    withContext(dispatcher) {
        subredditService.unFollowSubreddit(userName, subredditName)
    }
}

/**
 * Get all subreddits followed usecase
 *
 * @param dispatcher
 * @param subredditService
 */
fun getAllSubredditsFollowedUsecase(
    dispatcher: CoroutineDispatcher,
    subredditService: SubredditService
) = GetAllSubredditsFollowedUsecase { userName: UserName, limit: Int, offset: Long ->
    withContext(dispatcher) {
        subredditService.getAllSubredditsFollowed(userName, limit, offset)
    }
}