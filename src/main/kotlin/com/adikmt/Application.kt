package com.adikmt

import io.ktor.server.application.*
import com.adikmt.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureRouting()


    val factory = DatabaseFactoryImpl(environment.dbConfig("ktor.database"))

    factory.connect()
}
