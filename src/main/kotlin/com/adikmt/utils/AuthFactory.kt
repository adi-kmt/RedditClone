package com.adikmt.utils


import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.UserResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.jwt
import java.time.Duration
import java.util.*

data class JWTConfig(
    val secret: String,
    val validity: Duration,
)

class JwtService(private val jwtConfig: JWTConfig) {

    private val algorithm = Algorithm.HMAC256(jwtConfig.secret)

    val verifier: JWTVerifier =
        JWT.require(algorithm).build()

    fun generateToken(user: UserResponse): String = JWT.create()
        .withClaim("username", user.userName)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() = Date(System.currentTimeMillis() + jwtConfig.validity.toMillis())

    companion object {
        const val CONFIG_PATH = "ktor.jwt"
    }
}

fun ApplicationEnvironment.jwtConfig(path: String): JWTConfig = with(config.config(path)) {
    JWTConfig(
        secret = property("secret").getString(),
        validity = Duration.ofMillis(property("validity_ms").getString().toLong())
    )
}

fun AuthenticationConfig.configure(jwtService: JwtService) {
    jwt {
        authSchemes()
        verifier(jwtService.verifier)
        validate { jwtCredential: JWTCredential ->
            AuthCurrentUser(jwtCredential.getClaim("username", String::class).orEmpty())
        }
    }
}