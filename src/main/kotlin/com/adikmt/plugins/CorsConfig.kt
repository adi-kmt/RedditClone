package com.adikmt.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.plugins.cors.CORSConfig
import io.ktor.server.plugins.cors.maxAgeDuration
import kotlin.time.Duration.Companion.days

fun CORSConfig.cors() {
    allowMethod(HttpMethod.Options)
    allowMethod(HttpMethod.Get)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Delete)
    allowHeader(HttpHeaders.AccessControlAllowHeaders)
    allowHeader(HttpHeaders.AccessControlAllowOrigin)
    allowHeader(HttpHeaders.Authorization)
    allowHeader(HttpHeaders.ContentType)
    allowCredentials = true
    allowSameOrigin = true
    anyHost()
    maxAgeDuration = 1.days
}