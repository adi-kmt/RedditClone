package com.adikmt.utils

import com.adikmt.services.UserService
import com.adikmt.services.UserServiceImpl
import com.adikmt.usecases.AddUserUseCase
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetUserFollowingUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.SearchUserUseCase
import com.adikmt.usecases.UnFollowUserUseCase
import com.adikmt.usecases.addUserUseCase
import com.adikmt.usecases.followUserUseCase
import com.adikmt.usecases.getUserFollowingUseCase
import com.adikmt.usecases.getUserUseCase
import com.adikmt.usecases.searchUserUseCase
import com.adikmt.usecases.unFollowUserUseCase
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
}

private val usecases = module {
    //User usecase functions
    factory<AddUserUseCase> { addUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserUseCase> { getUserUseCase(get(named("IODispatcher")), get()) }
    factory<SearchUserUseCase> { searchUserUseCase(get(named("IODispatcher")), get()) }
    factory<GetUserFollowingUseCase> { getUserFollowingUseCase(get(named("IODispatcher")), get()) }
    factory<FollowUserUseCase> { followUserUseCase(get(named("IODispatcher")), get()) }
    factory<UnFollowUserUseCase> { unFollowUserUseCase(get(named("IODispatcher")), get()) }


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