package com.adikmt.plugins

import com.adikmt.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

val V1 = "v1"

fun Application.configureRouting() {
    routing {
//        authRoutes()
        userRoutes()
//        subredditRoutes()
//        postRoutes()
//        commentRoutes()
    }
}
