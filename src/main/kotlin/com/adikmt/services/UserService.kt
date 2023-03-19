package com.adikmt.services

import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList

interface UserService {

    suspend fun addUser(userRequest: UserRequest): Result<UserResponse>

    suspend fun getUserByUserName(userName: UserName): Result<UserResponse>

    suspend fun searchByUserName(userName: UserName): Result<UserResponseList>

    suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData>

    suspend fun followUser(userName: UserName): Result<UserFollowingData>

    suspend fun unfollowUser(userName: UserName): Result<UserFollowingData>
}

class UserServiceImpl : UserService {
    override suspend fun addUser(userRequest: UserRequest): Result<UserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUserName(userName: UserName): Result<UserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchByUserName(userName: UserName): Result<UserResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData> {
        TODO("Not yet implemented")
    }

    override suspend fun followUser(userName: UserName): Result<UserFollowingData> {
        TODO("Not yet implemented")
    }

    override suspend fun unfollowUser(userName: UserName): Result<UserFollowingData> {
        TODO("Not yet implemented")
    }
}