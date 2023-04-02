package com.adikmt

import com.adikmt.plugins.configure
import com.adikmt.plugins.configureRouting
import com.adikmt.plugins.cors
import com.adikmt.utils.JwtService
import com.adikmt.utils.configure
import com.adikmt.utils.db.DataBaseFactory
import com.adikmt.utils.db.dbConfig
import com.adikmt.utils.jwtConfig
import com.adikmt.utils.koinModules
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authentication
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level


/**
 * Main Entry point for the app
 *
 * @param args
 */
fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

/**
 * Common point to add dependencies for the entire app, in Ktor this is
 * achieved using install()
 */
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(koinModules: List<Module> = koinModules()) {
    install(Koin) {
        configure(koinModules)
    }

    val dbFactory by inject<DataBaseFactory> { parametersOf(environment.dbConfig("ktor.database")) }
    val json by inject<Json>()
    val jwtService by inject<JwtService> {
        parametersOf(environment.jwtConfig(JwtService.CONFIG_PATH))
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }
    install(ContentNegotiation) {
        KotlinxSerializer(json)
        json(json)
    }

    install(StatusPages) {
        configure()
    }

    /**
     * Used to handle double receiving, once with Call logging and other in the
     * routes
     */
    install(DoubleReceive)

    install(CallLogging) {
        level = Level.DEBUG
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        cors()
    }

    authentication {
        configure(jwtService)
    }

    configureRouting()

    dbFactory.connect()
}
