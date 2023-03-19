package com.adikmt.services

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentRequest
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.PostId
import com.adikmt.dtos.UserName

interface CommentService {
    suspend fun addComment(commentRequest: CommentRequest): Result<CommentResponse>

    suspend fun getComment(commentId: CommentId): Result<CommentResponse>

    suspend fun getAllCommentsByPost(postId: PostId): Result<CommentResponseList>

    suspend fun getAllCommentByUser(userName: UserName): Result<CommentResponseList>

    suspend fun upvoteComment(userName: UserName, commentId: CommentId): Result<CommentResponse>

    suspend fun downvoteComment(userName: UserName, commentId: CommentId): Result<CommentResponse>
}

class CommentServiceImpl : CommentService {
    override suspend fun addComment(commentRequest: CommentRequest): Result<CommentResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getComment(commentId: CommentId): Result<CommentResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCommentsByPost(postId: PostId): Result<CommentResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCommentByUser(userName: UserName): Result<CommentResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun upvoteComment(userName: UserName, commentId: CommentId): Result<CommentResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun downvoteComment(userName: UserName, commentId: CommentId): Result<CommentResponse> {
        TODO("Not yet implemented")
    }

}