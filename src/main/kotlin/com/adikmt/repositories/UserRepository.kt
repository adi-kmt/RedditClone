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

interface UserRepository {
    suspend fun createUser(userRequest: UserRequest): Result<UserResponse>

    suspend fun getUser(userName: UserName): Result<UserResponse?>

    suspend fun getUserLogin(userName: UserName): Result<LoginUserResponse?>

    suspend fun searchUserWithName(userName: UserName): Result<UserResponseList>

    suspend fun followUser(userName: UserName, userToFollow: UserName): Result<FollowOrUnfollowUser>

    suspend fun isFollowing(userName: UserName, userToFollow: UserName): Result<Boolean>

    suspend fun unFollowUser(userName: UserName, userToUnFollow: UserName): Result<FollowOrUnfollowUser>

    suspend fun getFollowersData(userName: UserName): Result<UserFollowingData>
}

class UserRepoImpl(private val dbTransaction: DbTransaction) : UserRepository {
    override suspend fun createUser(userRequest: UserRequest): Result<UserResponse> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = UserEntity.insertIgnoreAndGetId {
                    it[UserEntity.username] = userRequest.userName
                    it[UserEntity.email] = userRequest.userEmail
                    it[UserEntity.password] = userRequest.userPassword
                    it[UserEntity.bio] = userRequest.userBio.orEmpty()
                }
                UserResponse(
                    userId = id?.value,
                    userEmail = userRequest.userEmail,
                    userName = userRequest.userName,
                    userBio = userRequest.userPassword
                )
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

    override suspend fun searchUserWithName(userName: UserName): Result<UserResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                UserEntity.select {
                    UserEntity.username like userName.value
                }.map {
                    it.fromResultRowUser()
                }.distinct().toUserRepsponseList()
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