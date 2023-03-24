package com.adikmt.orm.helperfuncs

import com.adikmt.dtos.LoginUserResponse
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.orm.UserEntity
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.fromResultRowUser(): UserResponse = UserResponse(
    userId = this[UserEntity.id].value,
    userName = this[UserEntity.username],
    userEmail = this[UserEntity.email],
    userBio = this[UserEntity.bio]
)

fun ResultRow.fromResultRowUserLogin() = LoginUserResponse(
    userId = this[UserEntity.id].value,
    userName = this[UserEntity.username],
    userEmail = this[UserEntity.email],
    userBio = this[UserEntity.bio],
    userPassword = this[UserEntity.password]
)

fun toFollowerData(
    userName: UserName,
    usersFollowing: List<UserResponse>,
    othersFollowing: List<UserResponse>
): UserFollowingData = UserFollowingData(
    userName = userName.value,
    usersFollowing = usersFollowing,
    noUsersFollowing = usersFollowing.size,
    noOthersFollowingUser = othersFollowing.size,
    othersFollowingUser = othersFollowing
)


fun List<UserResponse>.toUserRepsponseList() = UserResponseList(
    userList = this,
    userNo = this.size
)
