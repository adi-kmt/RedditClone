package com.adikmt.routes

import io.ktor.server.application.call
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Routing.subredditRoutes() {
    addSubreddit()
    getSubredditByName()
    getFollowedSubreddit()
}

private fun Routing.getFollowedSubreddit() {
    get("/subreddit/{userId}") {
        val userId = call.parameters["userId"]
        userId?.let {

        }
    }
}

private fun Routing.getSubredditByName() {
    get("/subreddit/{name}") {
        val name = call.parameters["name"]
        name?.let {

        }
    }
}

private fun Routing.addSubreddit() {
    post("") {
    }
}
