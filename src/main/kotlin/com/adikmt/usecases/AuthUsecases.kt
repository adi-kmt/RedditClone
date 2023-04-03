package com.adikmt.usecases

import com.adikmt.dtos.LoginUser
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.services.AuthService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/** Register usecase interface */
fun interface RegisterUsecase {
    /**
     * Register
     *
     * @param userRequest
     * @return
     */
    suspend fun register(userRequest: UserRequest): Result<UserResponse?>
}

/**
 * Login usecase interface
 *
 * @constructor Create empty Login usecase
 */
fun interface LoginUsecase {
    /**
     * Login
     *
     * @param loginUser
     * @return
     */
    suspend fun login(loginUser: LoginUser): Result<UserResponse>
}

/**
 * Register usecase Impl
 *
 * @param dispatcher
 * @param authService
 */
fun registerUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = RegisterUsecase { userRequest: UserRequest ->
    withContext(dispatcher) {
        authService.register(userRequest)
    }
}

/**
 * Login usecase Impl
 *
 * @param dispatcher
 * @param authService
 */
fun loginUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = LoginUsecase { loginUser: LoginUser ->
    withContext(dispatcher) {
        authService.login(loginUser)
    }
}