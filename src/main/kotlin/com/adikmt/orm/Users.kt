package com.adikmt.orm

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UserEntity : LongIdTable(name = "users") {
    val email = varchar(name = "user_email", length = 100).uniqueIndex()
    val username = varchar(name = "user_name", length = 30).uniqueIndex()
    val password = varchar(name = "user_password", length = 50)
    val bio = text(name = "user_bio")
}

object UserFollowersEntity : Table("users_follow") {
    val userId =
        reference(name = "user_id", refColumn = UserEntity.username, onDelete = ReferenceOption.CASCADE)
    val followeeId =
        reference(name = "followee_id", refColumn = UserEntity.username, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(userId, followeeId)
}