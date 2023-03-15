package com.adikmt.utils.db

import com.adikmt.orm.SubredditEntity
import com.adikmt.orm.SubredditFollowerEntity
import com.adikmt.orm.UserEntity
import com.adikmt.orm.UserFollowersEntity
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object SchemaCreation {

    private val tables: Array<Table> = arrayOf(
        UserEntity,
        UserFollowersEntity,
        SubredditEntity,
        SubredditFollowerEntity
    )

    fun createSchema() {
        transaction { SchemaUtils.create(*tables) }
    }
}