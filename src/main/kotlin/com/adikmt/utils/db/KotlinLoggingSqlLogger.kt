package com.adikmt.utils.db

import mu.KotlinLogging
import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs
import org.jetbrains.exposed.sql.transactions.TransactionManager

object KotlinLoggingSqlLogger : SqlLogger {
    private val logger = KotlinLogging.logger("RW-Exposed")

    override fun log(context: StatementContext, transaction: Transaction) {
        logger.debug { context.expandArgs(TransactionManager.current()) }
    }
}