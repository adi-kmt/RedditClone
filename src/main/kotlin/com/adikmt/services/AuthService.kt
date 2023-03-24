package com.adikmt.services

import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.repositories.UserRepository
import com.adikmt.utils.checkPassword
import com.adikmt.utils.hash

interface AuthService {
    suspend fun register(userRequest: UserRequest): Result<UserResponse>

    suspend fun login(userRequest: UserRequest): Result<UserResponse>
}

class AuthServiceImpl(private val userRepository: UserRepository) : AuthService {
    override suspend fun register(userRequest: UserRequest): Result<UserResponse> {
        val hashedPassword = hash(userRequest.userPassword)
        return userRepository.createUser(userRequest, hashedPassword)
    }

    override suspend fun login(userRequest: UserRequest): Result<UserResponse> {
        val response = userRepository.getUserLogin(UserName(userRequest.userName))
        response.getOrNull()?.let { user ->
            if (checkPassword(userRequest.userPassword, user.userPassword)) {
                return Result.success(
                    UserResponse(
                        user.userId,
                        user.userName,
                        user.userEmail,
                        user.userBio
                    )
                )
            } else {
                return Result.failure(Exception("Inaccurate Password Provided"))
            }
        } ?: return Result.failure(response.exceptionOrNull() ?: Exception("Could not authenticate user"))
    }
}