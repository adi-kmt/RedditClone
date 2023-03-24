package com.adikmt.plugins

import com.adikmt.routes.authRoutes
import com.adikmt.routes.commentRoutes
import com.adikmt.routes.postRoutes
import com.adikmt.routes.subredditRoutes
import com.adikmt.routes.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        authRoutes()
        userRoutes()
        subredditRoutes()
        postRoutes()
        commentRoutes()
    }
}
