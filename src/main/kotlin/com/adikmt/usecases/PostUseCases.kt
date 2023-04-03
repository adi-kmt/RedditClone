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

/** Add post usecase */
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
 * Get post usecase
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
 * Get post feed by user usecase
 *
 * @constructor Create empty Get post feed by user usecase
 */
fun interface GetPostFeedByUserUsecase {
    /**
     * Get
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun get(userName: UserName?, limit: Int, offset: Long): Result<PostResponseList>
}

/**
 * Search post by heading usecase
 *
 * @constructor Create empty Search post by heading usecase
 */
fun interface SearchPostByHeadingUsecase {
    /**
     * Search
     *
     * @param postHeading
     * @param limit
     * @param offset
     * @return
     */
    suspend fun search(postHeading: PostHeading, limit: Int, offset: Long): Result<PostResponseList>
}

/**
 * Get post by subreddit usecase
 *
 * @constructor Create empty Get post by subreddit usecase
 */
fun interface GetPostBySubredditUsecase {
    /**
     * Get
     *
     * @param subredditName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun get(subredditName: SubredditName, limit: Int, offset: Long): Result<PostResponseList>
}

/**
 * Get post by user usecase
 *
 * @constructor Create empty Get post by user usecase
 */
fun interface GetPostByUserUsecase {
    /**
     * Get
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun get(userName: UserName, limit: Int, offset: Long): Result<PostResponseList>
}

/**
 * Upvote post usecase
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
 * Downvote post usecase
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
 * Add post usecase
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
 * Get post feed usecase
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostFeedUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostFeedByUserUsecase { userName: UserName?, limit: Int, offset: Long ->
    withContext(dispatcher) {
        postServices.getPostFeed(userName, limit, offset)
    }
}

/**
 * Get post usecase
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
 * Search post by heading usecase
 *
 * @param dispatcher
 * @param postServices
 */
fun searchPostByHeadingUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = SearchPostByHeadingUsecase { postHeading: PostHeading, limit: Int, offset: Long ->
    withContext(dispatcher) {
        postServices.searchPostByHeading(postHeading, limit, offset)
    }
}

/**
 * Get post by subreddit usecase
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostBySubredditUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostBySubredditUsecase { subredditName: SubredditName, limit: Int, offset: Long ->
    withContext(dispatcher) {
        postServices.getPostBySubreddit(subredditName, limit, offset)
    }
}

/**
 * Get post by user usecase
 *
 * @param dispatcher
 * @param postServices
 */
fun getPostByUserUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostByUserUsecase { userName: UserName, limit: Int, offset: Long ->
    withContext(dispatcher) {
        postServices.getPostByUser(userName, limit, offset)
    }
}

/**
 * Upvote post usecase
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
 * Downvote post usecase
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