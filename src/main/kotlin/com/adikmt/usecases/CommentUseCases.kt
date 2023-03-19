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

fun interface AddCommentUsecase {
    suspend fun add(commentRequest: CommentRequest): Result<CommentResponse>
}

fun interface GetCommentUsecase {
    suspend fun get(commentId: CommentId): Result<CommentResponse>
}

fun interface GetAllCommentsByPostUsecase {
    suspend fun get(postId: PostId): Result<CommentResponseList>
}

fun interface GetAllCommentByUserUsecase {
    suspend fun get(userName: UserName): Result<CommentResponseList>
}

fun interface UpvoteCommentUsecase {
    suspend fun upvote(userName: UserName, commentId: CommentId): Result<CommentResponse>
}

fun interface DownvoteCommentUsecase {
    suspend fun downvote(userName: UserName, commentId: CommentId): Result<CommentResponse>
}

fun addCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = AddCommentUsecase { commentRequest: CommentRequest ->
    withContext(dispatcher) {
        commentService.addComment(commentRequest)
    }
}

fun getCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetCommentUsecase { commentId: CommentId ->
    withContext(dispatcher) {
        commentService.getComment(commentId)
    }
}

fun getAllCommentsByPostUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetAllCommentsByPostUsecase { postId: PostId ->
    withContext(dispatcher) {
        commentService.getAllCommentsByPost(postId)
    }
}

fun getAllCommentByUserUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = GetAllCommentByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        commentService.getAllCommentByUser(userName)
    }
}

fun upvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = UpvoteCommentUsecase { userName: UserName, commentId: CommentId ->
    withContext(dispatcher) {
        commentService.upvoteComment(userName, commentId)
    }
}

fun downvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = DownvoteCommentUsecase { userName: UserName, commentId: CommentId ->
    withContext(dispatcher) {
        commentService.downvoteComment(userName, commentId)
    }
}
