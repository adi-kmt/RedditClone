package com.adikmt.usecases

import com.adikmt.dtos.FollowOrUnfollowUser
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun interface GetUserUseCase {
    suspend fun getUser(userName: UserName): Result<UserResponse?>
}

fun interface SearchUserUseCase {
    suspend fun searchUser(userName: UserName): Result<UserResponseList>
}

fun interface GetUserFollowingUseCase {
    suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData>
}

fun interface FollowUserUseCase {
    suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser>
}

fun interface UnFollowUserUseCase {
    suspend fun unFollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser>
}

fun getUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    GetUserUseCase { userName: UserName ->
        withContext(dispatcher) {
            userService.getUserByUserName(userName)
        }
    }
} catch (e: Exception) {
    Result.failure<UserResponse>(e)
}

fun searchUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    SearchUserUseCase { userName: UserName ->
        withContext(dispatcher) {
            userService.searchByUserName(userName)
        }
    }
} catch (e: Exception) {
    Result.failure<UserResponseList>(e)
}

fun getUserFollowingUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    GetUserFollowingUseCase { userName: UserName ->
        withContext(dispatcher) {
            userService.getUserFollowingData(userName)
        }
    }
} catch (e: Exception) {
    Result.failure<UserFollowingData>(e)
}

fun followUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    FollowUserUseCase { userName: UserName, userToFollow: UserName ->
        withContext(dispatcher) {
            userService.followUser(userName, userToFollow)
        }
    }
} catch (e: Exception) {
    Result.failure<FollowOrUnfollowUser>(e)
}

fun unFollowUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = try {
    UnFollowUserUseCase { userName: UserName, userToUnFollow: UserName ->
        withContext(dispatcher) {
            userService.unfollowUser(userName, userToUnFollow)
        }
    }
} catch (e: Exception) {
    Result.failure<FollowOrUnfollowUser>(e)
}

