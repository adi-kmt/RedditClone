package com.adikmt.services

import com.adikmt.dtos.UserResponse
import com.adikmt.repositories.UserRepository

interface AuthService {
    suspend fun register(): Result<UserResponse>

    suspend fun login(): Result<UserResponse>
}

class AuthServiceImpl(private val userRepository: UserRepository) : AuthService {
    override suspend fun register(): Result<UserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun login(): Result<UserResponse> {
        TODO("Not yet implemented")
    }

}