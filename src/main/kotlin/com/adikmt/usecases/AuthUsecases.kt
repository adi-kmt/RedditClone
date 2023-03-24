package com.adikmt.usecases

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.AuthUserResponse
import com.adikmt.services.AuthService
import kotlinx.coroutines.CoroutineDispatcher

fun interface RegisterUsecase {
    suspend fun register(): Result<AuthUserResponse>
}

fun interface LoginUsecase {
    suspend fun login(): Result<AuthCurrentUser>
}

fun interface CurrentUserUserUsecase {
    suspend fun getCurrentUser(): Result<AuthCurrentUser>
}

fun registerUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = RegisterUsecase {
    TODO()
}

fun loginUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = LoginUsecase {
    TODO()
}

fun currentUserUserUsecase(
    dispatcher: CoroutineDispatcher,
    authService: AuthService
) = CurrentUserUserUsecase {
    TODO()
}