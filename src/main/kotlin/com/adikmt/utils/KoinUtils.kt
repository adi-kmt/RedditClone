package com.adikmt.utils

import com.adikmt.utils.db.DatabaseFactoryImpl
import com.adikmt.utils.db.DataBaseFactory
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


private val mainModule = module {
    single<DataBaseFactory> { params ->
        DatabaseFactoryImpl(databaseConfig = params.get())
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