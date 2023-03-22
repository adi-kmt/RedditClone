package com.adikmt.utils

import com.adikmt.repositories.SubredditRepoImpl
import com.adikmt.repositories.SubredditRepository
import com.adikmt.repositories.UserRepoImpl
import com.adikmt.repositories.UserRepository
import com.adikmt.services.CommentService
import com.adikmt.services.CommentServiceImpl
import com.adikmt.services.PostServices
import com.adikmt.services.PostServicesImpl
import com.adikmt.services.SubredditService
import com.adikmt.services.SubredditServiceImpl
import com.adikmt.services.UserService
import com.adikmt.services.UserServiceImpl
import com.adikmt.usecases.AddCommentUsecase
import com.adikmt.usecases.AddPostUsecase
import com.adikmt.usecases.AddSubredditUsecase
import com.adikmt.usecases.AddUserUseCase
import com.adikmt.usecases.DownvoteCommentUsecase
import com.adikmt.usecases.DownvotePostUsecase
import com.adikmt.usecases.FollowSubredditUsecase
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetAllCommentByUserUsecase
import com.adikmt.usecases.GetAllCommentsByPostUsecase
import com.adikmt.usecases.GetAllSubredditsFollowedUsecase
import com.adikmt.usecases.GetCommentUsecase
import com.adikmt.usecases.GetPostBySubredditUsecase
import com.adikmt.usecases.GetPostByUserUsecase
import com.adikmt.usecases.GetPostFeedByUserUsecase
import com.adikmt.usecases.GetPostUsecase
import com.adikmt.usecases.GetSubredditByNameUsecase
import com.adikmt.usecases.GetUserFollowingUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.SearchPostByHeadingUsecase
import com.adikmt.usecases.SearchSubredditByNameUsecase
import com.adikmt.usecases.SearchUserUseCase
import com.adikmt.usecases.UnFollowSubredditUsecase
import com.adikmt.usecases.UnFollowUserUseCase
import com.adikmt.usecases.UpvoteCommentUsecase
import com.adikmt.usecases.UpvotePostUsecase
import com.adikmt.usecases.addCommentUsecase
import com.adikmt.usecases.addPostUsecase
import com.adikmt.usecases.addSubredditUsecase
import com.adikmt.usecases.addUserUseCase
import com.adikmt.usecases.downvoteCommentUsecase
import com.adikmt.usecases.downvotePostUsecase
import com.adikmt.usecases.followSubredditUsecase
import com.adikmt.usecases.followUserUseCase
import com.adikmt.usecases.getAllCommentByUserUsecase
import com.adikmt.usecases.getAllCommentsByPostUsecase
import com.adikmt.usecases.getAllSubredditsFollowedUsecase
import com.adikmt.usecases.getCommentUsecase
import com.adikmt.usecases.getPostBySubredditUsecase
import com.adikmt.usecases.getPostByUserUsecase
import com.adikmt.usecases.getPostFeedUsecase
import com.adikmt.usecases.getPostUsecase
import com.adikmt.usecases.getSubredditByNameUsecase
import com.adikmt.usecases.getUserFollowingUseCase
import com.adikmt.usecases.getUserUseCase
import com.adikmt.usecases.searchPostByHeadingUsecase
import com.adikmt.usecases.searchSubredditByNameUsecase
import com.adikmt.usecases.searchUserUseCase
import com.adikmt.usecases.unFollowSubredditUsecase
import com.adikmt.usecases.unFollowUserUseCase
import com.adikmt.usecases.upvoteCommentUsecase
import com.adikmt.usecases.upvotePostUsecase
import com.adikmt.utils.db.DataBaseFactory
import com.adikmt.utils.db.DatabaseFactoryImpl
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.db.DbTransactionImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun KoinApplication.configure(koinModules: List<Module>) {
    allowOverride(true)
    modules(koinModules)
}

fun koinModules() = listOf(mainModule, coroutinesModule, services, usecases, repositories)

private val coroutinesModule = module {
    single<CoroutineDispatcher>(named("IODispatcher")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("DefaultDispatcher")) { Dispatchers.Default }
    single<CoroutineDispatcher>(named("Main")) { Dispatchers.Main }
}

private val services = module {
    single<UserService> { UserServiceImpl(get()) }
    single<SubredditService> { SubredditServiceImpl(get()) }
    single<PostServices> { PostServicesImpl() }
    single<CommentService> { CommentServiceImpl() }
}

private val usecases = module {
    //User usecase functions
    factory<AddUserUseCase>(named("AddUserUseCase")) { addUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserUseCase>(named("GetUserUseCase")) { getUserUseCase(get(named("IODispatcher")), get()) }
    factory<SearchUserUseCase>(named("SearchUserUseCase")) { searchUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserFollowingUseCase>(named("GetUserFollowingUseCase")) {
        getUserFollowingUseCase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<FollowUserUseCase>(named("FollowUserUseCase")) { followUserUseCase(get(named("IODispatcher")), get()) }
    factory<UnFollowUserUseCase>(named("UnFollowUserUseCase")) {
        unFollowUserUseCase(
            get(named("IODispatcher")),
            get()
        )
    }

    //Subreddit usecase functions
    factory<AddSubredditUsecase>(named("AddSubredditUsecase")) {
        addSubredditUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<GetSubredditByNameUsecase>(named("GetSubredditByNameUsecase")) {
        getSubredditByNameUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<SearchSubredditByNameUsecase>(named("SearchSubredditByNameUsecase")) {
        searchSubredditByNameUsecase(
            get(
                named("IODispatcher")
            ), get()
        )
    }
    factory<FollowSubredditUsecase>(named("FollowSubredditUsecase")) {
        followSubredditUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<UnFollowSubredditUsecase>(named("UnFollowSubredditUsecase")) {
        unFollowSubredditUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<GetAllSubredditsFollowedUsecase>(named("GetAllSubredditsFollowedUsecase")) {
        getAllSubredditsFollowedUsecase(
            get(named("IODispatcher")),
            get()
        )
    }

    //Post usecase functions
    factory<AddPostUsecase>(named("AddPostUsecase")) { addPostUsecase(get(named("IODispatcher")), get()) }
    factory<GetPostUsecase>(named("GetPostUsecase")) { getPostUsecase(get(named("IODispatcher")), get()) }
    factory<GetPostFeedByUserUsecase>(named("GetPostFeedByUserUsecase")) {
        getPostFeedUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<SearchPostByHeadingUsecase>(named("SearchPostByHeadingUsecase")) {
        searchPostByHeadingUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<GetPostBySubredditUsecase>(named("GetPostBySubredditUsecase")) {
        getPostBySubredditUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<GetPostByUserUsecase>(named("GetPostByUserUsecase")) {
        getPostByUserUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<UpvotePostUsecase>(named("UpvotePostUsecase")) { upvotePostUsecase(get(named("IODispatcher")), get()) }
    factory<DownvotePostUsecase>(named("DownvotePostUsecase")) {
        downvotePostUsecase(
            get(named("IODispatcher")),
            get()
        )
    }

    //Comment usecase functions
    factory<AddCommentUsecase>(named("AddCommentUsecase")) { addCommentUsecase(get(named("IODispatcher")), get()) }
    factory<GetCommentUsecase>(named("GetCommentUsecase")) { getCommentUsecase(get(named("IODispatcher")), get()) }
    factory<GetAllCommentsByPostUsecase>(named("GetAllCommentsByPostUsecase")) {
        getAllCommentsByPostUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<GetAllCommentByUserUsecase>(named("GetAllCommentByUserUsecase")) {
        getAllCommentByUserUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<UpvoteCommentUsecase>(named("UpvoteCommentUsecase")) {
        upvoteCommentUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
    factory<DownvoteCommentUsecase>(named("DownvoteCommentUsecase")) {
        downvoteCommentUsecase(
            get(named("IODispatcher")),
            get()
        )
    }
}

private val repositories = module {
    single<UserRepository> { UserRepoImpl(get()) }
    single<SubredditRepository> { SubredditRepoImpl(get()) }
}


private val mainModule = module {
    single<DataBaseFactory> { params ->
        DatabaseFactoryImpl(databaseConfig = params.get())
    }
    single<DbTransaction> {
        DbTransactionImpl(get(named("IODispatcher")))
    }
    single {
        Json {
            encodeDefaults = true
            isLenient = true
            prettyPrint = false
            coerceInputValues = true
        }
    }
}