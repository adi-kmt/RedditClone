package com.adikmt.usecases

import com.adikmt.dtos.FollowOrUnfollowUser
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/** Get user use case */
fun interface GetUserUseCase {
    /**
     * Get user
     *
     * @param userName
     * @return
     */
    suspend fun getUser(userName: UserName): Result<UserResponse?>
}

/**
 * Search user use case
 *
 * @constructor Create empty Search user use case
 */
fun interface SearchUserUseCase {
    /**
     * Search user
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun searchUser(userName: UserName, limit: Int, offset: Long): Result<UserResponseList>
}

/**
 * Get user following use case
 *
 * @constructor Create empty Get user following use case
 */
fun interface GetUserFollowingUseCase {
    /**
     * Get user following data
     *
     * @param userName
     * @return
     */
    suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData>
}

/**
 * Follow user use case
 *
 * @constructor Create empty Follow user use case
 */
fun interface FollowUserUseCase {
    /**
     * Follow user
     *
     * @param userName
     * @param userToFollow
     * @return
     */
    suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser>
}

/**
 * Un follow user use case
 *
 * @constructor Create empty Un follow user use case
 */
fun interface UnFollowUserUseCase {
    /**
     * Un follow user
     *
     * @param userName
     * @param userToUnFollow
     * @return
     */
    suspend fun unFollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser>
}

/**
 * Get user use case
 *
 * @param dispatcher
 * @param userService
 */
fun getUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = GetUserUseCase { userName: UserName ->
    withContext(dispatcher) {
        userService.getUserByUserName(userName)
    }
}

/**
 * Search user use case
 *
 * @param dispatcher
 * @param userService
 */
fun searchUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = SearchUserUseCase { userName: UserName, limit: Int, offset: Long ->
    withContext(dispatcher) {
        userService.searchByUserName(userName, limit, offset)
    }
}

/**
 * Get user following use case
 *
 * @param dispatcher
 * @param userService
 */
fun getUserFollowingUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = GetUserFollowingUseCase { userName: UserName ->
    withContext(dispatcher) {
        userService.getUserFollowingData(userName)
    }
}

/**
 * Follow user use case
 *
 * @param dispatcher
 * @param userService
 */
fun followUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = FollowUserUseCase { userName: UserName, userToFollow: UserName ->
    withContext(dispatcher) {
        userService.followUser(userName, userToFollow)
    }
}

/**
 * Un follow user use case
 *
 * @param dispatcher
 * @param userService
 */
fun unFollowUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = UnFollowUserUseCase { userName: UserName, userToUnFollow: UserName ->
    withContext(dispatcher) {
        userService.unfollowUser(userName, userToUnFollow)
    }
}

