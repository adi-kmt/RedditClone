package com.adikmt.plugins

import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDatabases() {
    val database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )
    routing {
    }
}
