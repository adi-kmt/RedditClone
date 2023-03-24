package com.adikmt.usecases

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.services.AuthService
import com.adikmt.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun interface RegisterUsecase {
    suspend fun register(userRequest: UserRequest): Result<UserResponse>
}

fun interface LoginUsecase {
    suspend fun login(userRequest: UserRequest): Result<UserResponse>
}

fun interface CurrentUserUserUsecase {
    suspend fun getCurrentUser(userName: String?): AuthCurrentUser?
}

fun registerUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = RegisterUsecase { userRequest: UserRequest ->
    withContext(dispatcher) {
        authService.register(userRequest)
    }
}

fun loginUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = LoginUsecase { userRequest: UserRequest ->
    withContext(dispatcher) {
        authService.login(userRequest)
    }
}

fun currentUserUserUsecase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = CurrentUserUserUsecase { userName: String? ->
    withContext(dispatcher) {
        userName?.let { user ->
            userService.getUserByUserName(UserName(user)).getOrNull()?.let {
                return@let AuthCurrentUser(it.userName)
            }
        }
    }
}