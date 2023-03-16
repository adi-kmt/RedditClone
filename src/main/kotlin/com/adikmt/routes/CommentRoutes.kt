package com.adikmt.routes

import io.ktor.server.routing.Routing

fun Routing.commentRoutes() {
    addComment()
    getCommentById()
    getCommentsByPost()
    getCommentByUserId()
    upvoteComment()
    downvoteComment()
}

private fun Routing.downvoteComment() {
    TODO("Not yet implemented")
}

private fun Routing.upvoteComment() {
    TODO("Not yet implemented")
}

private fun Routing.getCommentByUserId() {
    TODO("Not yet implemented")
}

private fun Routing.getCommentById() {
    TODO("Not yet implemented")
}

private fun Routing.getCommentsByPost() {
    TODO("Not yet implemented")
}

private fun Routing.addComment() {
    TODO("Not yet implemented")
}
