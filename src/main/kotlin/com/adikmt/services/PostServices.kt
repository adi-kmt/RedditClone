package com.adikmt.services

import com.adikmt.dtos.PostHeading
import com.adikmt.dtos.PostId
import com.adikmt.dtos.PostRequest
import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.UserName
import com.adikmt.repositories.PostRepository

interface PostServices {
    suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse?>

    suspend fun getPost(postId: PostId): Result<PostResponse?>

    suspend fun getPostFeed(userName: UserName): Result<PostResponseList>

    suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList>

    suspend fun getPostBySubreddit(subredditName: SubredditName): Result<PostResponseList>

    suspend fun getPostByUser(userName: UserName): Result<PostResponseList>

    suspend fun upvotePost(postId: PostId, userName: UserName): Result<PostId>

    suspend fun downvotePost(postId: PostId, userName: UserName): Result<PostId>
}

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