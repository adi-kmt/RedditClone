package com.adikmt.services

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.repositories.SubredditRepository

/**
 * Subreddit service
 *
 */
interface SubredditService {
    /**
     * Add subreddit
     *
     * @param userName
     * @param subredditRequest
     * @return
     */
    suspend fun addSubreddit(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse?>

    /**
     * Get subreddit by name
     *
     * @param subredditName
     * @return
     */
    suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?>

    /**
     * Search subreddit by name
     *
     * @param subredditName
     * @return
     */
    suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList>

    /**
     * Follow subreddit
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    /**
     * Un follow subreddit
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun unFollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    /**
     * Get all subreddits followed
     *
     * @param userName
     * @return
     */
    suspend fun getAllSubredditsFollowed(userName: UserName): Result<SubredditResponseList>
}

/**
 * Subreddit service impl
 *
 * @constructor Create empty Subreddit service impl
 * @property subredditRepository
 */
class SubredditServiceImpl(private val subredditRepository: SubredditRepository) : SubredditService {
    override suspend fun addSubreddit(
        userName: UserName,
        subredditRequest: SubredditRequest
    ): Result<SubredditResponse?> =
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