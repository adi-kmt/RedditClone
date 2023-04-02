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

fun interface AddPostUsecase {
    suspend fun add(userName: UserName, postRequest: PostRequest): Result<PostResponse?>
}

fun interface GetPostUsecase {
    suspend fun get(postId: PostId): Result<PostResponse?>
}

fun interface GetPostFeedByUserUsecase {
    suspend fun get(userName: UserName): Result<PostResponseList>
}

fun interface SearchPostByHeadingUsecase {
    suspend fun search(postHeading: PostHeading): Result<PostResponseList>
}

fun interface GetPostBySubredditUsecase {
    suspend fun get(subredditName: SubredditName): Result<PostResponseList>
}

fun interface GetPostByUserUsecase {
    suspend fun get(userName: UserName): Result<PostResponseList>
}

fun interface UpvotePostUsecase {
    suspend fun upvote(userName: UserName, postId: PostId): Result<PostId>
}

fun interface DownvotePostUsecase {
    suspend fun downvote(userName: UserName, postId: PostId): Result<PostId>
}

fun addPostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = AddPostUsecase { userName: UserName, postRequest: PostRequest ->
    withContext(dispatcher) {
        postServices.addPost(userName, postRequest)
    }
}

fun getPostFeedUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostFeedByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        postServices.getPostFeed(userName)
    }
}

fun getPostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostUsecase { postId: PostId ->
    withContext(dispatcher) {
        postServices.getPost(postId)
    }
}

fun searchPostByHeadingUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = SearchPostByHeadingUsecase { postHeading: PostHeading ->
    withContext(dispatcher) {
        postServices.searchPostByHeading(postHeading)
    }
}

fun getPostBySubredditUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostBySubredditUsecase { subredditName: SubredditName ->
    withContext(dispatcher) {
        postServices.getPostBySubreddit(subredditName)
    }
}

fun getPostByUserUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = GetPostByUserUsecase { userName: UserName ->
    withContext(dispatcher) {
        postServices.getPostByUser(userName)
    }
}

fun upvotePostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = UpvotePostUsecase { userName: UserName, postId: PostId ->
    withContext(dispatcher) {
        postServices.upvotePost(postId, userName)
    }
}

fun downvotePostUsecase(
    dispatcher: CoroutineDispatcher,
    postServices: PostServices
) = DownvotePostUsecase { userName: UserName, postId: PostId ->
    withContext(dispatcher) {
        postServices.downvotePost(postId, userName)
    }
}