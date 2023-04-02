package com.adikmt.routes

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.SerializedException
import com.adikmt.dtos.UserName
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetUserFollowingUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.SearchUserUseCase
import com.adikmt.usecases.UnFollowUserUseCase
import com.adikmt.utils.deconstructResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Routing.userRoutes() {
    getProfile()
    followUser()
    unFollowUser()
    getProfileFollowingData()
    searchUser()
}

private fun Routing.searchUser() {
    val searchUserUseCase by inject<SearchUserUseCase>(named("SearchUserUseCase"))
    authenticate {
        get("/profiles/{username}") {
            try {
                val userName = call.parameters["username"]
                userName?.let {
                    val users = searchUserUseCase.searchUser(UserName(it))
                    deconstructResult(this, users, HttpStatusCode.OK)
                }
                call.respond(HttpStatusCode.UnprocessableEntity)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.getProfileFollowingData() {
    val getUserFollowingUseCase by inject<GetUserFollowingUseCase>(named("GetUserFollowingUseCase"))
    authenticate {
        get("/profiles/{username}/followingData") {
            try {
                val userName = call.parameters["username"]
                userName?.let {
                    val data = getUserFollowingUseCase.getUserFollowingData(UserName(it))
                    deconstructResult(this, data, HttpStatusCode.OK)
                }
                call.respond(HttpStatusCode.UnprocessableEntity)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.followUser() {
    val followUserUsecase by inject<FollowUserUseCase>(named("FollowUserUseCase"))
    authenticate {
        post("/profiles/{username}/follow") {
            try {
                val name = call.parameters["username"]
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { userName ->
                    name?.let {
                        val followed = followUserUsecase.followUser(UserName(userName), UserName(it))
                        deconstructResult(this, followed, HttpStatusCode.Created)
                    }
                    call.respond(HttpStatusCode.UnprocessableEntity)
                }
                call.respond(HttpStatusCode.Unauthorized)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.unFollowUser() {
    val unFollowUserUseCase by inject<UnFollowUserUseCase>(named("UnFollowUserUseCase"))
    authenticate {
        delete("/profiles/{username}/follow") {
            try {
                val name = call.parameters["username"]
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { userName ->
                    name?.let {
                        val unfollowed = unFollowUserUseCase.unFollowUser(UserName(userName), UserName(it))
                        deconstructResult(this, unfollowed, HttpStatusCode.Gone)
                    }
                    call.respond(HttpStatusCode.UnprocessableEntity)
                }
                call.respond(HttpStatusCode.Unauthorized)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.getProfile() {
    val getUserUseCase: GetUserUseCase by inject<GetUserUseCase>(named("GetUserUseCase"))
    authenticate {
        get("/profiles/id/{username}") {
            try {
                val userName = call.parameters["username"]
                userName?.let {
                    val profile = getUserUseCase.getUser(UserName(it))
                    deconstructResult(this, profile, HttpStatusCode.OK)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}