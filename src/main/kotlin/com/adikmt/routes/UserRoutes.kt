package com.adikmt.routes

import com.adikmt.routes.locations.FollowUserProfileLocation
import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Routing.userRoutes() {
    getProfile()
    followUser()
    unFollowUser()
}

//TODO not all locations working??
private fun Routing.unFollowUser() {
    post<FollowUserProfileLocation> { params ->
        val userName = params.userName
        userName?.let {

        }
    }
}

private fun Routing.followUser() {
    delete("/profiles/{username}/follow") {
        val userName = call.parameters["username"]

        userName?.let {
        }
    }
}

private fun Routing.getProfile() {
    get("/profiles/{username}") {
        val userName = call.parameters["username"]
        userName?.let {

        }
    }
}