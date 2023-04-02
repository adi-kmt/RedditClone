package com.adikmt.services

import com.adikmt.dtos.PostHeading
import com.adikmt.dtos.PostId
import com.adikmt.dtos.PostRequest
import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.UserName
import com.adikmt.repositories.PostRepository

/**
 * Post services
 *
 */
interface PostServices {
    /**
     * Add post
     *
     * @param userName
     * @param postRequest
     * @return
     */
    suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse?>

    /**
     * Get post
     *
     * @param postId
     * @return
     */
    suspend fun getPost(postId: PostId): Result<PostResponse?>

    /**
     * Get post feed
     *
     * @param userName
     * @return
     */
    suspend fun getPostFeed(userName: UserName): Result<PostResponseList>

    /**
     * Search post by heading
     *
     * @param postHeading
     * @return
     */
    suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList>

    /**
     * Get post by subreddit
     *
     * @param subredditName
     * @return
     */
    suspend fun getPostBySubreddit(subredditName: SubredditName): Result<PostResponseList>

    /**
     * Get post by user
     *
     * @param userName
     * @return
     */
    suspend fun getPostByUser(userName: UserName): Result<PostResponseList>

    /**
     * Upvote post
     *
     * @param postId
     * @param userName
     * @return
     */
    suspend fun upvotePost(postId: PostId, userName: UserName): Result<PostId>

    /**
     * Downvote post
     *
     * @param postId
     * @param userName
     * @return
     */
    suspend fun downvotePost(postId: PostId, userName: UserName): Result<PostId>
}

/**
 * Post services impl
 *
 * @constructor Create empty Post services impl
 * @property postRepository
 */
class PostServicesImpl(private val postRepository: PostRepository) : PostServices {
    override suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse?> =
        postRepository.addPost(userName, postRequest)

    override suspend fun getPost(postId: PostId): Result<PostResponse?> =
        postRepository.getPostById(postId)

    override suspend fun getPostFeed(userName: UserName): Result<PostResponseList> =
        postRepository.getPostFeed(userName)

    override suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList> =
        postRepository.searchPostByHeading(postHeading)

    override suspend fun getPostBySubreddit(subredditName: SubredditName): Result<PostResponseList> =
        postRepository.getPostListBySubredditName(subredditName)

    override suspend fun getPostByUser(userName: UserName): Result<PostResponseList> =
        postRepository.getPostListByUser(userName)

    override suspend fun upvotePost(postId: PostId, userName: UserName): Result<PostId> =
        postRepository.upvotePost(userName, postId)

    override suspend fun downvotePost(postId: PostId, userName: UserName): Result<PostId> =
        postRepository.downvotePost(userName, postId)

}