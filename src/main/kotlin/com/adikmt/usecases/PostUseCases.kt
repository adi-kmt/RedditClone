package com.adikmt.usecases

import com.adikmt.dtos.PostHeading
import com.adikmt.dtos.PostId
import com.adikmt.dtos.PostRequest
import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.UserName
import com.adikmt.services.PostServices
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Add post usecase interface
 *
 * @constructor Create empty Add post usecase
 */
fun interface AddPostUsecase {
    /**
     * Add
     *
     * @param userName
     * @param postRequest
     * @return
     */
    suspend fun add(userName: UserName, postRequest: PostRequest): Result<PostResponse?>
}

/**
 * Get post usecase interface
 *
 * @constructor Create empty Get post usecase
 */
fun interface GetPostUsecase {
    /**
     * Get
     *
     * @param postId
     * @return
     */
    suspend fun get(postId: PostId): Result<PostResponse?>
}

/**
 * Get post feed by user usecase interface
 *
 * @constructor Create empty Get post feed by user usecase
 */
fun interface GetPostFeedByUserUsecase {
    /**
     * Get
     *
     * @param userName
     * @return
     */
    suspend fun get(userName: UserName): Result<PostResponseList>
}

/**
 * Search post by heading usecase interface
 *
 * @constructor Create empty Search post by heading usecase
 */
fun interface SearchPostByHeadingUsecase {
    /**
     * Search
     *
     * @param postHeading
     * @return
     */
    suspend fun search(postHeading: PostHeading): Result<PostResponseList>
}

/**
 * Get post by subreddit usecase interface
 *
 * @constructor Create empty Get post by subreddit usecase
 */
fun interface GetPostBySubredditUsecase {
    /**
     * Get
     *
     * @param subredditName
     * @return
     */
    suspend fun get(subredditName: SubredditName): Result<PostResponseList>
}

/**
 * Get post by user usecase interface
 *
 * @constructor Create empty Get post by user usecase
 */
fun interface GetPostByUserUsecase {
    /**
     * Get
     *
     * @param userName
     * @return
     */
    suspend fun get(userName: UserName): Result<PostResponseList>
}

/**
 * Upvote post usecase interface
 *
 * @constructor Create empty Upvote post usecase
 */
fun interface UpvotePostUsecase {
    /**
     * Upvote
     *
     * @param userName
     * @param postId
     * @return
     */
    suspend fun upvote(userName: UserName, postId: PostId): Result<PostId>
}

/**
 * Downvote post usecase interface
 *
 * @constructor Create empty Downvote post usecase
 */
fun interface DownvotePostUsecase {
    /**
     * Downvote
     *
     * @param userName
     * @param postId
     * @return
     */
    suspend fun downvote(userName: UserName, postId: PostId): Result<PostId>
}

/**
 * Add post usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun addPostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = AddPostUsecase { userName: UserName, postRequest: PostRequest ->
    withContext(dispatcher) {
        postServices.addPost(userName, postRequest)
    }
}

/**
 * Get post feed usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostFeedUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostFeedByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        postServices.getPostFeed(userName)
    }
}

/**
 * Get post usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostUsecase { postId: PostId ->
    withContext(dispatcher) {
        postServices.getPost(postId)
    }
}

/**
 * Search post by heading usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun searchPostByHeadingUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = SearchPostByHeadingUsecase { postHeading: PostHeading ->
    withContext(dispatcher) {
        postServices.searchPostByHeading(postHeading)
    }
}

/**
 * Get post by subreddit usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostBySubredditUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostBySubredditUsecase { subredditName: SubredditName ->
    withContext(dispatcher) {
        postServices.getPostBySubreddit(subredditName)
    }
}

/**
 * Get post by user usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostByUserUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        postServices.getPostByUser(userName)
    }
}

/**
 * Upvote post usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun upvotePostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = UpvotePostUsecase { userName: UserName, postId: PostId ->
    withContext(dispatcher) {
        postServices.upvotePost(postId, userName)
    }
}

/**
 * Downvote post usecase impl
 *
 * @param dispatcher
 * @param postServices
 */
fun downvotePostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = DownvotePostUsecase { userName: UserName, postId: PostId ->
    withContext(dispatcher) {
        postServices.downvotePost(postId, userName)
    }
}