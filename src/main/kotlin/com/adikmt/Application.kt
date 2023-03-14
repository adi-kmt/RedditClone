package com.adikmt

import com.adikmt.plugins.configureRouting
import com.adikmt.utils.configure
import com.adikmt.utils.cors
import com.adikmt.utils.db.DataBaseFactory
import com.adikmt.utils.db.dbConfig
import com.adikmt.utils.koinModules
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(koinModules:List<Module> = koinModules()) {
    install(Koin) {
        configure(koinModules)
    }

    val dbFactory by inject<DataBaseFactory> { parametersOf(environment.dbConfig("ktor.database")) }
    val json by inject<Json>()


    install(DefaultHeaders){
        header("X-Engine", "Ktor")
    }
    install(ContentNegotiation) { json(json) }

    install(StatusPages) {
        configure()
    }

    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS){
        cors()
    }

    configureRouting()

    dbFactory.connect()
}
