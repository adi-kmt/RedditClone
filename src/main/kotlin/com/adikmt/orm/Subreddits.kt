package com.adikmt.orm

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object SubredditEntity: UUIDTable(name = "subreddits"){
    val title = varchar(name = "subreddit_name", length = 80)
    val desc = text(name = "subreddit_desc")
}

object SubredditFollowerEntity: Table(){
    val subredditId = reference("subreddit_id", SubredditEntity, onDelete = ReferenceOption.CASCADE)
    val userId = reference("user_id", UserEntity, onDelete = ReferenceOption.CASCADE)
}