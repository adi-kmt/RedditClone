package com.adikmt.orm

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object SubredditEntity : LongIdTable(name = "subreddits") {
    val title = varchar(name = "subreddit_name", length = 80)
    val desc = text(name = "subreddit_desc").nullable()
}

object SubredditFollowerEntity : Table(name = "subreddits_user_follow") {
    val subredditId = reference("subreddit_id", SubredditEntity, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", UserEntity, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(subredditId, userId)
}