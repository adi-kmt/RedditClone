package com.adikmt.services

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentRequest
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.PostId
import com.adikmt.dtos.UserName
import com.adikmt.repositories.CommentRepository

/** Comment service */
interface CommentService {
    /**
     * Add comment
     *
     * @param userName
     * @param commentRequest
     * @return
     */
    suspend fun addComment(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?>

    /**
     * Get comment
     *
     * @param commentId
     * @return
     */
    suspend fun getComment(commentId: CommentId): Result<CommentResponse?>

    /**
     * Get all comments by post
     *
     * @param postId
     * @return
     */
    suspend fun getAllCommentsByPost(postId: PostId): Result<CommentResponseList>

    /**
     * Get all comment by user
     *
     * @param userName
     * @return
     */
    suspend fun getAllCommentByUser(userName: UserName): Result<CommentResponseList>

    /**
     * Upvote comment
     *
     * @param userName
     * @param commentId
     * @return
     */
    suspend fun upvoteComment(userName: UserName, commentId: CommentId): Result<CommentId>

    /**
     * Downvote comment
     *
     * @param userName
     * @param commentId
     * @return
     */
    suspend fun downvoteComment(userName: UserName, commentId: CommentId): Result<CommentId>
}

/**
 * Comment service impl
 *
 * @constructor Create empty Comment service impl
 * @property commentRepository
 */
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