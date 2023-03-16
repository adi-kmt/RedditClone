package com.adikmt.routes

import io.ktor.server.routing.Routing

fun Routing.postRoutes() {
    addPost()
    getPostFeed()
    getPostById()
    getPostListByHeading()
    getPostListBySubredditName()
    getPostListByUserId()
    upvotePostById()
    downvotePostById()
}

private fun Routing.getPostFeed() {
    //TODO check auth -> Then share by list as followed
    TODO("Not yet implemented")
}

private fun Routing.downvotePostById() {
    TODO("Not yet implemented")
}

private fun Routing.upvotePostById() {
    TODO("Not yet implemented")
}

private fun Routing.getPostListByUserId() {
    TODO("Not yet implemented")
}

private fun Routing.getPostListBySubredditName() {
    TODO("Not yet implemented")
}

private fun Routing.getPostListByHeading() {
    TODO("Not yet implemented")
}

private fun Routing.getPostById() {
    TODO("Not yet implemented")
}

private fun Routing.addPost() {
    TODO("Not yet implemented")
}
