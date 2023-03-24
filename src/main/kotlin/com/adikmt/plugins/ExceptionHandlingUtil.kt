package com.adikmt.plugins

import com.adikmt.dtos.SerializedException
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond

fun StatusPagesConfig.configure() {
    exception<Throwable> { call, throwable ->
        call.respond(HttpStatusCode.InternalServerError, SerializedException(throwable.message))
    }
}