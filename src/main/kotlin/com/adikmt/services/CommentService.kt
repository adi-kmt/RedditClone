package com.adikmt.services

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentRequest
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.PostId
import com.adikmt.dtos.UserName
import com.adikmt.repositories.CommentRepository

interface CommentService {
    suspend fun addComment(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?>

    suspend fun getComment(commentId: CommentId): Result<CommentResponse?>

    suspend fun getAllCommentsByPost(postId: PostId): Result<CommentResponseList>

    suspend fun getAllCommentByUser(userName: UserName): Result<CommentResponseList>

    suspend fun upvoteComment(userName: UserName, commentId: CommentId): Result<CommentId>

    suspend fun downvoteComment(userName: UserName, commentId: CommentId): Result<CommentId>
}

class CommentServiceImpl(private val commentRepository: CommentRepository) : CommentService {
    override suspend fun addComment(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?> =
        commentRepository.addComment(userName, commentRequest)

    override suspend fun getComment(commentId: CommentId): Result<CommentResponse?> =
        commentRepository.getCommentById(commentId)

    override suspend fun getAllCommentsByPost(postId: PostId): Result<CommentResponseList> =
        commentRepository.getCommentListByPostId(postId)

    override suspend fun getAllCommentByUser(userName: UserName): Result<CommentResponseList> =
        commentRepository.getCommentListByUserName(userName)

    override suspend fun upvoteComment(userName: UserName, commentId: CommentId): Result<CommentId> =
        commentRepository.upvoteComment(commentId, userName)

    override suspend fun downvoteComment(userName: UserName, commentId: CommentId): Result<CommentId> =
        commentRepository.downvoteComment(commentId, userName)
}