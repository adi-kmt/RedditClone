package com.adikmt.plugins

import com.adikmt.routes.commentRoutes
import com.adikmt.routes.postRoutes
import com.adikmt.routes.subredditRoutes
import com.adikmt.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

val V1 = "v1"

fun Application.configureRouting() {
    routing {
        route(V1) {
            userRoutes()
            subredditRoutes()
            postRoutes()
            commentRoutes()
        }
    }
}
