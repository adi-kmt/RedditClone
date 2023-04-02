package com.adikmt.services

import com.adikmt.dtos.FollowOrUnfollowUser
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.repositories.UserRepository

/** User service */
interface UserService {
    /**
     * Get user by user name
     *
     * @param userName
     * @return
     */
    suspend fun getUserByUserName(userName: UserName): Result<UserResponse?>

    /**
     * Search by user name
     *
     * @param userName
     * @return
     */
    suspend fun searchByUserName(userName: UserName): Result<UserResponseList>

    /**
     * Get user following data
     *
     * @param userName
     * @return
     */
    suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData>

    /**
     * Follow user
     *
     * @param userName
     * @param userToFollow
     * @return
     */
    suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser>

    /**
     * Unfollow user
     *
     * @param userName
     * @param userToUnFollow
     * @return
     */
    suspend fun unfollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser>
}

/**
 * User service impl
 *
 * @constructor Create empty User service impl
 * @property userRepository
 */
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override suspend fun getUserByUserName(userName: UserName): Result<UserResponse?> =
        userRepository.getUser(userName)

    override suspend fun searchByUserName(userName: UserName): Result<UserResponseList> =
        userRepository.searchUserWithName(userName)

    override suspend fun getUserFollowingData(userName: UserName): Result<UserFollowingData> =
        userRepository.getFollowersData(userName)

    override suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser> =
        userRepository.followUser(userName = userName, userToFollow = userToFollow)

    override suspend fun unfollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser> =
        userRepository.unFollowUser(userName = userName, userToUnFollow = userToUnFollow)
}