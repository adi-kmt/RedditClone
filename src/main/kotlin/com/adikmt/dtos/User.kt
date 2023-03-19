package com.adikmt.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val userName: String,
    val userPassword: String,
    val userEmail: String,
    val userBio: String?
)

@Serializable
data class UserResponse(
    val userName: String,
    val userEmail: String,
    val userBio: String?
)

@Serializable
data class UserFollowingData(
    val userName: String,
    val usersFollowing: List<UserResponse>,
    val noUsersFollowing: Int,
    val othersFollowingUser: List<UserResponse>,
    val noOthersFollowingUser: Int
)

@Serializable
data class FollowOrUnfollowUser(
    val userName: UserName
)

@Serializable
data class UserResponseList(
    val userList: List<UserResponse>,
    val userNo: Int
)

@JvmInline
@Serializable
value class UserName(val value: String)