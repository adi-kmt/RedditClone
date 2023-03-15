package com.adikmt.orm

import com.adikmt.orm.PostEntity.nullable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserEntity : UUIDTable(name = "users") {
    val email = varchar(name = "user_email", length = 100).uniqueIndex()
    val username = varchar(name = "user_name", length = 30).uniqueIndex()
    val password = varchar(name = "user_password", length = 50)
    val bio = text(name = "user_bio").nullable()
}

object UserFollowersEntity : Table("users_follow") {
    val userId =
        reference(name = "user_id", foreign = UserEntity, onDelete = ReferenceOption.CASCADE)
    val followeeId = reference(name = "followee_id", foreign = UserEntity, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(userId, followeeId)
}