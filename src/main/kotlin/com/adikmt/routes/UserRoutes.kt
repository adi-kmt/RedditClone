package com.adikmt.routes

import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.services.UserServiceImpl
import com.adikmt.usecases.AddUserUseCase
import com.adikmt.usecases.FollowUserUseCase
import com.adikmt.usecases.GetUserUseCase
import com.adikmt.usecases.addUserUseCase
import com.adikmt.usecases.unFollowUserUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Routing.userRoutes() {
    getProfile()
    followUser()
    unFollowUser()
    addProfile()
}

//TODO not all locations working??
private fun Routing.followUser() {
    val followUserUsecase by inject<FollowUserUseCase>()

    post("/profiles/{username}/follow") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                followUserUsecase.followUser(UserName(it))
                call.respond(HttpStatusCode.Created, UserName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e)
        }
    }
}

private fun Routing.unFollowUser() {
//    val unFollowUserUseCase by inject<UnFollowUserUseCase>()

    delete("/profiles/{username}/follow") {
        try {
            val userName = call.parameters["username"]

            userName?.let {
                unFollowUserUseCase(Dispatchers.IO, UserServiceImpl()).unFollowUser(UserName(it))
//                unFollowUserUseCase.unFollowUser(UserName(it))
                call.respond(HttpStatusCode.Gone)
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e)
        }
    }
}

private fun Routing.getProfile() {
    val getUserUseCase: GetUserUseCase by inject<GetUserUseCase>(named("GetUserUseCase"))

    get("/profiles/{username}") {
        try {
            val userName = call.parameters["username"]
            userName?.let {
                getUserUseCase.getUser(UserName(it))
                call.respond(HttpStatusCode.OK, UserName(it))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e)
        }
    }
}

//TODO Temporary -> to be removed
private fun Routing.addProfile() {
    val addUserUseCase by inject<AddUserUseCase>(named("AddUserUseCase"))

    post("/profiles") {
        try {
            val user = call.receive<UserRequest>()
            user?.let {
                addUserUseCase.add(user)
                addUserUseCase(Dispatchers.IO, UserServiceImpl()).add(it)
                call.respond(HttpStatusCode.Created, UserName(it.userName))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e)
        }
    }
}