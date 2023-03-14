package com.adikmt.utils.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.time.Duration.Companion.milliseconds


private val logger = KotlinLogging.logger {}
class DatabaseFactoryImpl(val databaseConfig: DatabaseConfig): DataBaseFactory {
    override fun connect() {
        logger.info { "Initializing DB connection" }
        Database.connect(hikari(), databaseConfig = config())
        SchemaCreation.createSchema()
        logger.info { "DB initialization complete" }
    }

    private fun config(): org.jetbrains.exposed.sql.DatabaseConfig = DatabaseConfig {
        sqlLogger = KotlinLoggingSqlLogger
        useNestedTransactions = true
        warnLongQueriesDuration = 50.milliseconds.inWholeMilliseconds
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            jdbcUrl = databaseConfig.url
            driverClassName = databaseConfig.driver
            username = databaseConfig.username
            password = databaseConfig.password
            maximumPoolSize = databaseConfig.maxPoolSize

            addDataSourceProperty("rewriteBatchedStatements", true)
        }.also { it.validate() }
        return HikariDataSource(config)
    }

    override suspend fun <T> dbQuery(block: () -> T): T =
        transaction { block() }

    override suspend fun dropAll() {
        TODO("Not yet implemented")
    }

}

fun ApplicationEnvironment.dbConfig(path: String): DatabaseConfig = with(config.config(path)) {
    DatabaseConfig(driver = property("driver").getString(),
        url = property("url").getString(),
        username = property("username").getString(),
        password = property("pwd").getString(),
        maxPoolSize = property("maxPoolSize").getString().toInt())
}
