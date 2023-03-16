package com.adikmt.routes.locations

import io.ktor.locations.Location


@Location("/profiles/{username}")
data class UserProfileLocation(val userName: String)

@Location("/profiles/{username}/follow")
data class FollowUserProfileLocation(val userName: String)

@Location("/profiles/{username}/unfollow")
data class UnFollowUserProfileLocation(val userName: String)