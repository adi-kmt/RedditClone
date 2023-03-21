package com.adikmt.routes

import com.adikmt.dtos.SerializedException
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.UserName
import com.adikmt.usecases.AddSubredditUsecase
import com.adikmt.usecases.FollowSubredditUsecase
import com.adikmt.usecases.GetAllSubredditsFollowedUsecase
import com.adikmt.usecases.GetSubredditByNameUsecase
import com.adikmt.usecases.SearchSubredditByNameUsecase
import com.adikmt.usecases.UnFollowSubredditUsecase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

fun Routing.subredditRoutes() {
    addSubreddit()
    getSubredditByName()
    searchSubredditByName()
    getFollowedSubreddit()
    followSubreddit()
    unfollowSubreddit()
}

private fun Routing.unfollowSubreddit() {
    val unFollowSubredditUsecase by inject<UnFollowSubredditUsecase>(named("UnFollowSubredditUsecase"))
    delete("/subreddit/unFollow/{name}") {
        try {
            val name = call.parameters["name"]
            name?.let {
                unFollowSubredditUsecase.unFollow(UserName(""), SubredditName(it))
                call.respond(HttpStatusCode.OK, SubredditName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.followSubreddit() {
    val followSubredditUsecase by inject<FollowSubredditUsecase>(named("FollowSubredditUsecase"))
    post("/subreddit/follow/{name}") {
        try {
            val name = call.parameters["name"]
            name?.let {
                followSubredditUsecase.follow(UserName(""), SubredditName(name))
                call.respond(HttpStatusCode.OK, SubredditName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.searchSubredditByName() {
    val searchSubredditByNameUsecase by inject<SearchSubredditByNameUsecase>(named("SearchSubredditByNameUsecase"))
    get("/subreddit/{name}") {
        try {
            val name = call.parameters["name"]
            name?.let {
                searchSubredditByNameUsecase.get(SubredditName(it))
                call.respond(HttpStatusCode.OK, SubredditName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.getFollowedSubreddit() {
    val getAllSubredditsFollowedUsecase by inject<GetAllSubredditsFollowedUsecase>(named("GetAllSubredditsFollowedUsecase"))

    get("/subreddit/user/{userId}") {
        try {
            val userId = call.parameters["userId"]
            userId?.let {
                getAllSubredditsFollowedUsecase.get(UserName(it))
                call.respond(HttpStatusCode.OK, UserName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.getSubredditByName() {
    val getSubredditByNameUsecase by inject<GetSubredditByNameUsecase>(named("GetSubredditByNameUsecase"))
    get("/subreddit/id/{name}") {
        try {
            val name = call.parameters["name"]
            name?.let {
                getSubredditByNameUsecase.get(SubredditName(it))
                call.respond(HttpStatusCode.OK, SubredditName(it))
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}

private fun Routing.addSubreddit() {
    val addSubredditUsecase by inject<AddSubredditUsecase>(named("AddSubredditUsecase"))
    post("/subreddit") {
        try {
            val subreddit = call.receive<SubredditRequest>()
            subreddit?.let {
                addSubredditUsecase.add(subreddit)
                call.respond(HttpStatusCode.Created, SubredditResponse)
            }
            call.respond(HttpStatusCode.UnprocessableEntity)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
        }
    }
}
