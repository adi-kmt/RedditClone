package com.adikmt.routes.locations

import io.ktor.locations.Location

@Location("/subreddit")
data class AddSubredditLocation(val subredditDto: String) //To be changed

@Location("/subreddit/{name}")
data class GetSubredditByName(val subredditName: String)

@Location("/subreddit/{userId}")
data class GetFollowedSubreddit(val userId: String)