package com.adikmt.utils.db

import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface DbTransaction{
    suspend fun <T> dbQuery(block: () -> T): T
}

class DbTransactionImpl(private val coroutineDispatcher: CoroutineDispatcher): DbTransaction{
    override suspend fun <T> dbQuery(block: () -> T): T =
        newSuspendedTransaction(coroutineDispatcher) { block() }
}