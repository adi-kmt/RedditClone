package com.adikmt.usecases

import com.adikmt.dtos.FollowOrUnfollowUser
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.services.UserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun interface AddUserUseCase {
    suspend fun add(userRequest: UserRequest): Result<UserResponse>
}

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


fun addUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = AddUserUseCase { userRequest: UserRequest ->
    withContext(dispatcher) {
        userService.addUser(userRequest)
    }
}

fun getUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = GetUserUseCase { userName: UserName ->
    withContext(dispatcher) {
        userService.getUserByUserName(userName)
    }
}

fun searchUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = SearchUserUseCase { userName: UserName ->
    withContext(dispatcher) {
        userService.searchByUserName(userName)
    }
}

fun getUserFollowingUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = GetUserFollowingUseCase { userName: UserName ->
    withContext(dispatcher) {
        userService.getUserFollowingData(userName)
    }
}

fun followUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = FollowUserUseCase { userName: UserName, userToFollow: UserName ->
    withContext(dispatcher) {
        userService.followUser(userName, userToFollow)
    }
}

fun unFollowUserUseCase(
    dispatcher: CoroutineDispatcher,
    userService: UserService
) = UnFollowUserUseCase { userName: UserName, userToUnFollow: UserName ->
    withContext(dispatcher) {
        userService.unfollowUser(userName, userToUnFollow)
    }
}

