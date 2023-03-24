package com.adikmt.utils


import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.UserResponse
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.Principal
import io.ktor.server.auth.jwt.jwt
import java.time.Duration
import java.util.*

data class JWTConfig(
    val issuer: String,
    val audience: String,
    val realm: String,
    val secret: String,
    val validity: Duration,
)

class JwtService(private val jwtConfig: JWTConfig) {

    private val algorithm = Algorithm.HMAC256(jwtConfig.secret)
    val realm = jwtConfig.realm

    val verifier: JWTVerifier =
        JWT.require(algorithm).withIssuer(jwtConfig.issuer).withAudience(jwtConfig.audience).build()

    fun generateToken(user: UserResponse): String = JWT.create()
        .withSubject(user.userId.toString())
        .withIssuer(jwtConfig.issuer)
        .withAudience(jwtConfig.audience)
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
        issuer = property("issuer").getString(),
        audience = property("audience").getString(),
        realm = property("realm").getString(),
        secret = property("secret").getString(),
        validity = Duration.ofMillis(property("validity_ms").getString().toLong())
    )
}

fun AuthenticationConfig.configure(jwtService: JwtService, validate: suspend (AuthCurrentUser) -> Principal?) {
    jwt(name = "auth-jwt") {
        realm = jwtService.realm
        authSchemes("Token")
        verifier(jwtService.verifier)
        this.validate { credential ->
            credential.payload.subject?.let { userName ->
                validate(AuthCurrentUser(userName))
            }
        }
    }
}