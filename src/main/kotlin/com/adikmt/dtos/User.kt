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
data class UserResponseList(
    val userList: List<UserResponse>,
    val userNo: Int
)