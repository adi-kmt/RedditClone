package com.adikmt.utils.db

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object SchemaCreation {

    private val tables: Array<Table> = arrayOf()

    fun createSchema() {
        transaction { SchemaUtils.create(*tables) }
    }
}