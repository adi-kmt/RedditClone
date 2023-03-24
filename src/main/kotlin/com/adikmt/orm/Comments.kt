package com.adikmt.orm

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object CommentEntity : LongIdTable(name = "comments") {
    val text = text("comment_text")
    val createdAt = datetime("comment_datetime").defaultExpression(CurrentDateTime)
    val post = reference("comment_post", PostEntity)
    val author = reference("comment_author", refColumn = UserEntity.username)
    val parentComment = reference("parent_comment_id", CommentEntity).nullable()
}

object CommentFavouriteEntity : Table(name = "comment_user_fav") {
    val userId = reference("user_id", UserEntity.username, onDelete = ReferenceOption.CASCADE)
    val commentId = reference("comment_id", CommentEntity, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(userId, commentId)
}