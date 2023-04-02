package com.adikmt.usecases

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.LoginUser
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.services.AuthService
import com.adikmt.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun interface RegisterUsecase {
    suspend fun register(userRequest: UserRequest): Result<UserResponse?>
}

fun interface LoginUsecase {
    suspend fun login(loginUser: LoginUser): Result<UserResponse>
}

fun interface CurrentUserUsecase {
    suspend fun getCurrentUser(userName: String?): AuthCurrentUser?
}

fun registerUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = try {
    RegisterUsecase { userRequest: UserRequest ->
        withContext(dispatcher) {
            authService.register(userRequest)
        }
    }
} catch (e: Exception) {
    Result.failure<UserResponse>(e)
}

fun loginUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = try {
    LoginUsecase { loginUser: LoginUser ->
        withContext(dispatcher) {
            authService.login(loginUser)
        }
    }
} catch (e: Exception) {
    Result.failure<UserResponse>(e)
}

fun currentUserUserUsecase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    CurrentUserUsecase { userName: String? ->
        withContext(dispatcher) {
            userName?.let { user ->
                userService.getUserByUserName(UserName(user)).getOrNull()?.let {
                    return@let AuthCurrentUser(it.userName)
                }
            }
        }
    }
} catch (e: Exception) {
    Result.failure<AuthCurrentUser>(e)
}