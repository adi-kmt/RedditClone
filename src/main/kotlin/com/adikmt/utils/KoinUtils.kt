package com.adikmt.utils

import com.adikmt.services.PostServices
import com.adikmt.services.PostServicesImpl
import com.adikmt.services.SubredditService
import com.adikmt.services.SubredditServiceImpl
import com.adikmt.services.UserService
import com.adikmt.services.UserServiceImpl
import com.adikmt.usecases.AddPostUsecase
import com.adikmt.usecases.AddSubredditUsecase
import com.adikmt.usecases.AddUserUseCase
import com.adikmt.usecases.DownvotePostUsecase
import com.adikmt.usecases.FollowSubredditUsecase
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetAllSubredditsFollowedUsecase
import com.adikmt.usecases.GetPostBySubredditUsecase
import com.adikmt.usecases.GetPostByUserUsecase
import com.adikmt.usecases.GetPostUsecase
import com.adikmt.usecases.GetSubredditByNameUsecase
import com.adikmt.usecases.GetUserFollowingUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.SearchPostByHeadingUsecase
import com.adikmt.usecases.SearchSubredditByNameUsecase
import com.adikmt.usecases.SearchUserUseCase
import com.adikmt.usecases.UnFollowSubredditUsecase
import com.adikmt.usecases.UnFollowUserUseCase
import com.adikmt.usecases.UpvotePostUsecase
import com.adikmt.usecases.addPostUsecase
import com.adikmt.usecases.addSubredditUsecase
import com.adikmt.usecases.addUserUseCase
import com.adikmt.usecases.downvotePostUsecase
import com.adikmt.usecases.followSubredditUsecase
import com.adikmt.usecases.followUserUseCase
import com.adikmt.usecases.getAllSubredditsFollowedUsecase
import com.adikmt.usecases.getPostBySubredditUsecase
import com.adikmt.usecases.getPostByUserUsecase
import com.adikmt.usecases.getPostUsecase
import com.adikmt.usecases.getSubredditByNameUsecase
import com.adikmt.usecases.getUserFollowingUseCase
import com.adikmt.usecases.getUserUseCase
import com.adikmt.usecases.searchPostByHeadingUsecase
import com.adikmt.usecases.searchSubredditByNameUsecase
import com.adikmt.usecases.searchUserUseCase
import com.adikmt.usecases.unFollowSubredditUsecase
import com.adikmt.usecases.unFollowUserUseCase
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

fun koinModules() = listOf(mainModule, coroutinesModule)

private val coroutinesModule = module {
    single<CoroutineDispatcher>(named("IODispatcher")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("DefaultDispatcher")) { Dispatchers.Default }
    single<CoroutineDispatcher>(named("Main")) { Dispatchers.Main }
}

private val services = module {
    single<UserService> { UserServiceImpl() }
    single<SubredditService> { SubredditServiceImpl() }
    single<PostServices> { PostServicesImpl() }
}

private val usecases = module {
    //User usecase functions
    factory<AddUserUseCase> { addUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserUseCase> { getUserUseCase(get(named("IODispatcher")), get()) }
    factory<SearchUserUseCase> { searchUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserFollowingUseCase> { getUserFollowingUseCase(get(named("IODispatcher")), get()) }
    factory<FollowUserUseCase> { followUserUseCase(get(named("IODispatcher")), get()) }
    factory<UnFollowUserUseCase> { unFollowUserUseCase(get(named("IODispatcher")), get()) }

    //Subreddit usecase functions
    factory<AddSubredditUsecase> { addSubredditUsecase(get(named("IODispatcher")), get()) }
    factory<GetSubredditByNameUsecase> { getSubredditByNameUsecase(get(named("IODispatcher")), get()) }
    factory<SearchSubredditByNameUsecase> { searchSubredditByNameUsecase(get(named("IODispatcher")), get()) }
    factory<FollowSubredditUsecase> { followSubredditUsecase(get(named("IODispatcher")), get()) }
    factory<UnFollowSubredditUsecase> { unFollowSubredditUsecase(get(named("IODispatcher")), get()) }
    factory<GetAllSubredditsFollowedUsecase> { getAllSubredditsFollowedUsecase(get(named("IODispatcher")), get()) }

    //Post usecase functions
    factory<AddPostUsecase> { addPostUsecase(get(named("IODispatcher")), get()) }
    factory<GetPostUsecase> { getPostUsecase(get(named("IODispatcher")), get()) }
    factory<SearchPostByHeadingUsecase> { searchPostByHeadingUsecase(get(named("IODispatcher")), get()) }
    factory<GetPostBySubredditUsecase> { getPostBySubredditUsecase(get(named("IODispatcher")), get()) }
    factory<GetPostByUserUsecase> { getPostByUserUsecase(get(named("IODispatcher")), get()) }
    factory<UpvotePostUsecase> { upvotePostUsecase(get(named("IODispatcher")), get()) }
    factory<DownvotePostUsecase> { downvotePostUsecase(get(named("IODispatcher")), get()) }
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