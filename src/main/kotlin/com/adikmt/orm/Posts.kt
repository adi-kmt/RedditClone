package com.adikmt.orm

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.Instant
import java.time.LocalDateTime

object PostEntity: UUIDTable(name = "posts"){
    val title = varchar(name = "post_title", length = 255)
    val desc = text(name = "post_desc").nullable()
    val author = reference("post_author", UserEntity)
    val subreddit = reference("post_subreddit", SubredditEntity)
    val createdAt = datetime("post_datetime").defaultExpression(CurrentDateTime)
}

object PostFavouriteEntity: Table(name = "post_user_fav"){
    val userId =  reference("user_id", UserEntity, onDelete = ReferenceOption.CASCADE)
    val postId = reference("post_id", PostEntity, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(userId, postId)
}