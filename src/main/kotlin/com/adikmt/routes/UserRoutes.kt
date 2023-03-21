package com.adikmt.routes

import com.adikmt.dtos.SerializedException
import com.adikmt.dtos.UserName
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetUserFollowingUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.SearchUserUseCase
import com.adikmt.usecases.UnFollowUserUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
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

    get("/profiles/{username}") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                searchUserUseCase.searchUser(UserName(it))
                call.respond(HttpStatusCode.Created, UserName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.getProfileFollowingData() {
    val getUserFollowingUseCase by inject<GetUserFollowingUseCase>(named("GetUserFollowingUseCase"))

    get("/profiles/{username}/followingData") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                getUserFollowingUseCase.getUserFollowingData(UserName(it))
                call.respond(HttpStatusCode.Created, UserName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.followUser() {
    val followUserUsecase by inject<FollowUserUseCase>(named("FollowUserUseCase"))

    post("/profiles/{username}/follow") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                followUserUsecase.followUser(UserName(it))
                call.respond(HttpStatusCode.Created, UserName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.unFollowUser() {
    val unFollowUserUseCase by inject<UnFollowUserUseCase>(named("UnFollowUserUseCase"))

    delete("/profiles/{username}/follow") {
        try {
            val userName = call.parameters["username"]

            userName?.let {
                unFollowUserUseCase.unFollowUser(UserName(it))
                call.respond(HttpStatusCode.Gone)
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.getProfile() {
    val getUserUseCase: GetUserUseCase by inject<GetUserUseCase>(named("GetUserUseCase"))

    get("/profiles/id/{username}") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                getUserUseCase.getUser(UserName(it))
                call.respond(HttpStatusCode.OK, UserName(it))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}