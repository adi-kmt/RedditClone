package com.adikmt.usecases

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentRequest
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.PostId
import com.adikmt.dtos.UserName
import com.adikmt.services.CommentService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/** Add comment usecase interface */
fun interface AddCommentUsecase {
    /**
     * Add
     *
     * @param userName
     * @param commentRequest
     * @return
     */
    suspend fun add(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?>
}

/**
 * Get comment usecase interface
 *
 * @constructor Create empty Get comment usecase
 */
fun interface GetCommentUsecase {
    /**
     * Get
     *
     * @param commentId
     * @return
     */
    suspend fun get(commentId: CommentId): Result<CommentResponse?>
}

/**
 * Get all comments by post usecase interface
 *
 * @constructor Create empty Get all comments by post usecase
 */
fun interface GetAllCommentsByPostUsecase {
    /**
     * Get
     *
     * @param postId
     * @return
     */
    suspend fun get(postId: PostId): Result<CommentResponseList>
}

/**
 * Get all comment by user usecase interface
 *
 * @constructor Create empty Get all comment by user usecase
 */
fun interface GetAllCommentByUserUsecase {
    /**
     * Get
     *
     * @param userName
     * @return
     */
    suspend fun get(userName: UserName): Result<CommentResponseList>
}

/**
 * Upvote comment usecase interface
 *
 * @constructor Create empty Upvote comment usecase
 */
fun interface UpvoteCommentUsecase {
    /**
     * Upvote
     *
     * @param userName
     * @param commentId
     * @return
     */
    suspend fun upvote(userName: UserName, commentId: CommentId): Result<CommentId>
}

/**
 * Downvote comment usecase interface
 *
 * @constructor Create empty Downvote comment usecase
 */
fun interface DownvoteCommentUsecase {
    /**
     * Downvote
     *
     * @param userName
     * @param commentId
     * @return
     */
    suspend fun downvote(userName: UserName, commentId: CommentId): Result<CommentId>
}

/**
 * Add comment usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun addCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = AddCommentUsecase { userName: UserName, commentRequest: CommentRequest ->
    withContext(dispatcher) {
        commentService.addComment(userName, commentRequest)
    }
}

/**
 * Get comment usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun getCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetCommentUsecase { commentId: CommentId ->
    withContext(dispatcher) {
        commentService.getComment(commentId)
    }
}

/**
 * Get all comments by post usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun getAllCommentsByPostUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetAllCommentsByPostUsecase { postId: PostId ->
    withContext(dispatcher) {
        commentService.getAllCommentsByPost(postId)
    }
}

/**
 * Get all comment by user usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun getAllCommentByUserUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetAllCommentByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        commentService.getAllCommentByUser(userName)
    }
}

/**
 * Upvote comment usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun upvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = UpvoteCommentUsecase { userName: UserName, commentId: CommentId ->
    withContext(dispatcher) {
        commentService.upvoteComment(userName, commentId)
    }
}

/**
 * Downvote comment usecase impl
 *
 * @param dispatcher
 * @param commentService
 */
fun downvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = DownvoteCommentUsecase { userName: UserName, commentId: CommentId ->
    withContext(dispatcher) {
        commentService.downvoteComment(userName, commentId)
    }
}
