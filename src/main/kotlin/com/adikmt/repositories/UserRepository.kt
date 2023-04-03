package com.adikmt.repositories

import com.adikmt.dtos.FollowOrUnfollowUser
import com.adikmt.dtos.LoginUserResponse
import com.adikmt.dtos.UserFollowingData
import com.adikmt.dtos.UserName
import com.adikmt.dtos.UserRequest
import com.adikmt.dtos.UserResponse
import com.adikmt.dtos.UserResponseList
import com.adikmt.orm.UserEntity
import com.adikmt.orm.UserFollowersEntity
import com.adikmt.orm.helperfuncs.fromResultRowUser
import com.adikmt.orm.helperfuncs.fromResultRowUserLogin
import com.adikmt.orm.helperfuncs.toFollowerData
import com.adikmt.orm.helperfuncs.toUserRepsponseList
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.resultOf
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteIgnoreWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.select

/** User repository */
interface UserRepository {
    /**
     * Create user
     *
     * @param userRequest
     * @param hashedPassword
     * @return
     */
    suspend fun createUser(userRequest: UserRequest, hashedPassword: String): Result<UserResponse?>

    /**
     * Get user
     *
     * @param userName
     * @return
     */
    suspend fun getUser(userName: UserName): Result<UserResponse?>

    /**
     * Get user login
     *
     * @param userName
     * @return
     */
    suspend fun getUserLogin(userName: UserName): Result<LoginUserResponse?>

    /**
     * Search user with name
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun searchUserWithName(userName: UserName, limit: Int, offset: Long): Result<UserResponseList>

    /**
     * Follow user
     *
     * @param userName
     * @param userToFollow
     * @return
     */
    suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser>

    /**
     * Is following
     *
     * @param userName
     * @param userToFollow
     * @return
     */
    suspend fun isFollowing(userName: UserName, userToFollow: UserName): Result<Boolean>

    /**
     * Un follow user
     *
     * @param userName
     * @param userToUnFollow
     * @return
     */
    suspend fun unFollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser>

    /**
     * Get followers data
     *
     * @param userName
     * @return
     */
    suspend fun getFollowersData(userName: UserName): Result<UserFollowingData>
}

/**
 * User repo impl
 *
 * @constructor Create empty User repo impl
 * @property dbTransaction
 */
class UserRepoImpl(private val dbTransaction: DbTransaction) : UserRepository {
    override suspend fun createUser(userRequest: UserRequest, hashedPassword: String): Result<UserResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = UserEntity.insertIgnoreAndGetId {
                    it[UserEntity.username] = userRequest.userName
                    it[UserEntity.email] = userRequest.userEmail
                    it[UserEntity.password] = hashedPassword
                    it[UserEntity.bio] = userRequest.userBio.orEmpty()
                }
                id?.let {
                    UserResponse(
                        userId = it.value,
                        userEmail = userRequest.userEmail,
                        userName = userRequest.userName,
                        userBio = userRequest.userPassword
                    )
                }
            }
        }
    }

    override suspend fun getUser(userName: UserName): Result<UserResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                UserEntity.select {
                    UserEntity.username eq userName.value
                }.map {
                    it.fromResultRowUser()
                }.firstOrNull()
            }
        }
    }

    override suspend fun getUserLogin(userName: UserName): Result<LoginUserResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                UserEntity.select {
                    UserEntity.username eq userName.value
                }.map {
                    it.fromResultRowUserLogin()
                }.firstOrNull()
            }
        }
    }

    override suspend fun searchUserWithName(userName: UserName, limit: Int, offset: Long): Result<UserResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                UserEntity.select {
                    UserEntity.username like userName.value
                }
                    .limit(n = limit, offset = offset)
                    .map {
                        it.fromResultRowUser()
                    }.toUserRepsponseList(limit, offset)
            }
        }
    }

    override suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser> {
        return dbTransaction.dbQuery {
            resultOf {
                UserFollowersEntity.insertIgnore {
                    it[UserFollowersEntity.userId] = userToFollow.value
                    it[UserFollowersEntity.followeeId] = userName.value
                }
                FollowOrUnfollowUser(userToFollow)
            }
        }
    }

    override suspend fun isFollowing(userName: UserName, userToFollow: UserName): Result<Boolean> {
        return dbTransaction.dbQuery {
            resultOf {
                UserFollowersEntity.select {
                    UserFollowersEntity.userId eq userToFollow.value and (UserFollowersEntity.followeeId eq userName.value)
                }.empty().not()
            }
        }
    }

    override suspend fun unFollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser> {
        return dbTransaction.dbQuery {
            resultOf {
                UserFollowersEntity.deleteIgnoreWhere {
                    UserFollowersEntity.userId eq userToUnFollow.value and (UserFollowersEntity.followeeId eq userName.value)
                }
                FollowOrUnfollowUser(userToUnFollow)
            }
        }
    }

    override suspend fun getFollowersData(userName: UserName): Result<UserFollowingData> {
        return dbTransaction.dbQuery {
            resultOf {
                val otherFollowingUserData = UserFollowersEntity.select {
                    UserFollowersEntity.userId eq userName.value
                }.map { it.fromResultRowUser() }
                val usersFollowingData = UserFollowersEntity.select {
                    UserFollowersEntity.followeeId eq userName.value
                }.map { it.fromResultRowUser() }

                toFollowerData(
                    userName = userName,
                    othersFollowing = otherFollowingUserData,
                    usersFollowing = usersFollowingData
                )
            }
        }
    }

}