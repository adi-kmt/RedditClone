package com.adikmt.dtos

import io.ktor.server.auth.Principal
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
    val userId: Long,
    val userName: String,
    val userEmail: String,
    val userBio: String?
)

@Serializable
data class LoginUserResponse(
    val userId: Long,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userBio: String?
)

@Serializable
data class UserResponseWithToken(
    val userId: Long,
    val userName: String,
    val userEmail: String,
    val userBio: String?,
    val token: String
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
    val userNo: Int,
    val limit: Int,
    val offset: Long
)

@Serializable
data class AuthCurrentUser(val userName: String?) : Principal

@Serializable
data class LoginUser(val userName: String, val password: String)

@JvmInline
@Serializable
value class UserName(val value: String)