package com.adikmt.orm

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object SubredditEntity : LongIdTable(name = "subreddits") {
    val title = varchar(name = "subreddit_name", length = 80).uniqueIndex()
    val desc = text(name = "subreddit_desc").nullable()
    val createdByUser =
        reference(name = "created_by_user", refColumn = UserEntity.username, onDelete = ReferenceOption.CASCADE)
}

object SubredditFollowerEntity : Table(name = "subreddits_user_follow") {
    val subredditId = reference("subreddit_id", SubredditEntity.title, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", UserEntity.username, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(subredditId, userId)
}