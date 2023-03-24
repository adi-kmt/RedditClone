package com.adikmt.routes

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.LoginUser
import com.adikmt.dtos.SerializedException
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponseWithToken
import com.adikmt.usecases.LoginUsecase
import com.adikmt.usecases.RegisterUsecase
import com.adikmt.utils.JwtService
import com.adikmt.utils.jwtConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Routing.authRoutes() {
    registerUser()
    loginUser()
    getCurrentUser()
}

private fun Routing.getCurrentUser() {
    authenticate {
        get("/users") {
            try {
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { name ->
                    call.respond(HttpStatusCode.OK, UserName(name))
                }
                call.respond(HttpStatusCode.Unauthorized, SerializedException("No Logged in user found"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.loginUser() {
    val loginUsecase by inject<LoginUsecase>(named("LoginUsecase"))
    val jwtService by inject<JwtService> { parametersOf(application.environment.jwtConfig(JwtService.CONFIG_PATH)) }
    post("/login") {
        try {
            val user = call.receive<LoginUser>()
            val userResponse = loginUsecase.login(user)
            userResponse.getOrNull()?.let { response ->
                val token = jwtService.generateToken(response)
                call.respond(
                    HttpStatusCode.Created, UserResponseWithToken(
                        userId = response.userId,
                        userName = response.userName,
                        userEmail = response.userEmail,
                        userBio = response.userBio,
                        token = token
                    )
                )
            } ?: call.respond(
                HttpStatusCode.InternalServerError,
                userResponse.exceptionOrNull() ?: SerializedException("Login Failed")
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.registerUser() {
    val jwtService by inject<JwtService> { parametersOf(application.environment.jwtConfig(JwtService.CONFIG_PATH)) }
    val registerUsecase by inject<RegisterUsecase>(named("RegisterUsecase"))

    post("/register") {
        try {
            val user = call.receive<UserRequest>()
            val userResponse = registerUsecase.register(user)

            userResponse.getOrNull()?.let { response ->
                val token = jwtService.generateToken(response)

                call.respond(
                    HttpStatusCode.Created, UserResponseWithToken(
                        userId = response.userId,
                        userName = response.userName,
                        userEmail = response.userEmail,
                        userBio = response.userBio,
                        token = token
                    )
                )
            } ?: call.respond(
                HttpStatusCode.InternalServerError,
                SerializedException(userResponse.exceptionOrNull()?.message ?: "Registration Failed")
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}
