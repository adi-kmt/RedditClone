package com.adikmt.services

import com.adikmt.dtos.PostHeading
import com.adikmt.dtos.PostId
import com.adikmt.dtos.PostRequest
import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.UserName

interface PostServices {
    suspend fun addPost(postRequest: PostRequest): Result<PostResponse>

    suspend fun getPost(postId: PostId): Result<PostResponse>

    suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList>

    suspend fun getPostBySubreddit(subredditName: SubredditName): Result<PostResponseList>

    suspend fun getPostByUser(userName: UserName): Result<PostResponseList>

    suspend fun upvotePost(postId: PostId, userName: UserName): Result<PostResponse>

    suspend fun downvotePost(postId: PostId, userName: UserName): Result<PostResponse>
}

class PostServicesImpl : PostServices {
    override suspend fun addPost(postRequest: PostRequest): Result<PostResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: PostId): Result<PostResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostBySubreddit(subredditName: SubredditName): Result<PostResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostByUser(userName: UserName): Result<PostResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun upvotePost(postId: PostId, userName: UserName): Result<PostResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun downvotePost(postId: PostId, userName: UserName): Result<PostResponse> {
        TODO("Not yet implemented")
    }

}