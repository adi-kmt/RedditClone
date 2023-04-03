package com.adikmt.routes

import com.adikmt.dtos.AuthCurrentUser
import com.adikmt.dtos.SerializedException
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.UserName
import com.adikmt.usecases.AddSubredditUsecase
import com.adikmt.usecases.FollowSubredditUsecase
import com.adikmt.usecases.GetAllSubredditsFollowedUsecase
import com.adikmt.usecases.GetSubredditByNameUsecase
import com.adikmt.usecases.SearchSubredditByNameUsecase
import com.adikmt.usecases.UnFollowSubredditUsecase
import com.adikmt.utils.deconstructResult
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
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
    authenticate {
        delete("/subreddit/unFollow/{name}") {
            try {
                val name = call.parameters["name"]
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { userName ->
                    name?.let {
                        val data = unFollowSubredditUsecase.unFollow(UserName(userName), SubredditName(it))
                        deconstructResult(this, data, HttpStatusCode.Gone)
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

private fun Routing.followSubreddit() {
    val followSubredditUsecase by inject<FollowSubredditUsecase>(named("FollowSubredditUsecase"))
    authenticate {
        post("/subreddit/follow/{name}") {
            try {
                val name = call.parameters["name"]
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { userName ->
                    name?.let {
                        val data = followSubredditUsecase.follow(UserName(userName), SubredditName(name))
                        deconstructResult(this, data, HttpStatusCode.Created)
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

private fun Routing.searchSubredditByName() {
    val searchSubredditByNameUsecase by inject<SearchSubredditByNameUsecase>(named("SearchSubredditByNameUsecase"))
    authenticate {
        get("/subreddit/{name}") {
            try {
                val name = call.parameters["name"]
                val user = call.principal<AuthCurrentUser>()?.userName
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
                val offset = call.request.queryParameters["offset"]?.toLong() ?: 0L
                user?.let { userName ->
                    name?.let {
                        val subredditList = searchSubredditByNameUsecase.get(SubredditName(it), limit, offset)
                        deconstructResult(this, subredditList, HttpStatusCode.OK)
                    }
                    call.respond(HttpStatusCode.UnprocessableEntity)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.getFollowedSubreddit() {
    val getAllSubredditsFollowedUsecase by inject<GetAllSubredditsFollowedUsecase>(named("GetAllSubredditsFollowedUsecase"))
    authenticate {
        get("/subreddit/user/{userId}") {
            try {
                val userId = call.parameters["userId"]
                val user = call.principal<AuthCurrentUser>()?.userName
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 20
                val offset = call.request.queryParameters["offset"]?.toLong() ?: 0L
                user?.let { userName ->
                    userId?.let {
                        val subredditList = getAllSubredditsFollowedUsecase.get(UserName(it), limit, offset)
                        deconstructResult(this, subredditList, HttpStatusCode.OK)
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

private fun Routing.getSubredditByName() {
    val getSubredditByNameUsecase by inject<GetSubredditByNameUsecase>(named("GetSubredditByNameUsecase"))
    authenticate {
        get("/subreddit/id/{name}") {
            try {
                val name = call.parameters["name"]
                name?.let {
                    val subreddit = getSubredditByNameUsecase.get(SubredditName(it))
                    deconstructResult(this, subreddit, HttpStatusCode.OK)
                }
                call.respond(HttpStatusCode.UnprocessableEntity)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}

private fun Routing.addSubreddit() {
    val addSubredditUsecase by inject<AddSubredditUsecase>(named("AddSubredditUsecase"))
    authenticate {
        post("/subreddit") {
            try {
                val subreddit = call.receive<SubredditRequest>()
                val user = call.principal<AuthCurrentUser>()?.userName
                user?.let { userName ->
                    subreddit?.let {
                        val subredditResponse = addSubredditUsecase.add(UserName(userName), subreddit)
                        deconstructResult(this, subredditResponse, HttpStatusCode.Created)
                    } ?: run {
                        call.respond(HttpStatusCode.UnprocessableEntity)
                    }
                }
                call.respond(HttpStatusCode.Unauthorized)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, SerializedException(e.message))
            }
        }
    }
}
