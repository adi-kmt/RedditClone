package com.adikmt.utils.db

import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/** Db transaction extracted to separate */
interface DbTransaction {
    /**
     * Db query
     *
     * @param block
     * @param T
     * @return
     * @receiver
     */
    suspend fun <T> dbQuery(block: () -> T): T
}

/**
 * Db transaction impl
 *
 * @property coroutineDispatcher
 */
class DbTransactionImpl(private val coroutineDispatcher: CoroutineDispatcher) : DbTransaction {
    override suspend fun <T> dbQuery(block: () -> T): T =
        newSuspendedTransaction(coroutineDispatcher) { block() }
}