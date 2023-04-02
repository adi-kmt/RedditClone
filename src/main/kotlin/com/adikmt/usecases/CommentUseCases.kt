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
    suspend fun add(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?>
}

fun interface GetCommentUsecase {
    suspend fun get(commentId: CommentId): Result<CommentResponse?>
}

fun interface GetAllCommentsByPostUsecase {
    suspend fun get(postId: PostId): Result<CommentResponseList>
}

fun interface GetAllCommentByUserUsecase {
    suspend fun get(userName: UserName): Result<CommentResponseList>
}

fun interface UpvoteCommentUsecase {
    suspend fun upvote(userName: UserName, commentId: CommentId): Result<CommentId>
}

fun interface DownvoteCommentUsecase {
    suspend fun downvote(userName: UserName, commentId: CommentId): Result<CommentId>
}

fun addCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    AddCommentUsecase { userName: UserName, commentRequest: CommentRequest ->
        withContext(dispatcher) {
            commentService.addComment(userName, commentRequest)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentResponse>(e)
}

fun getCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    GetCommentUsecase { commentId: CommentId ->
        withContext(dispatcher) {
            commentService.getComment(commentId)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentResponse>(e)
}

fun getAllCommentsByPostUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    GetAllCommentsByPostUsecase { postId: PostId ->
        withContext(dispatcher) {
            commentService.getAllCommentsByPost(postId)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentResponseList>(e)
}

fun getAllCommentByUserUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    GetAllCommentByUserUsecase { userName: UserName ->
        withContext(dispatcher) {
            commentService.getAllCommentByUser(userName)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentResponseList>(e)
}

fun upvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    UpvoteCommentUsecase { userName: UserName, commentId: CommentId ->
        withContext(dispatcher) {
            commentService.upvoteComment(userName, commentId)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentId>(e)
}

fun downvoteCommentUsecase(
    dispatcher: CoroutineDispatcher,
    commentService: CommentService
) = try {
    DownvoteCommentUsecase { userName: UserName, commentId: CommentId ->
        withContext(dispatcher) {
            commentService.downvoteComment(userName, commentId)
        }
    }
} catch (e: Exception) {
    Result.failure<CommentId>(e)
}
