package com.adikmt.utils

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.configure() {
    exception<Throwable> {call, throwable ->
        call.respond(HttpStatusCode.InternalServerError, Exception(throwable.message))
    }
}