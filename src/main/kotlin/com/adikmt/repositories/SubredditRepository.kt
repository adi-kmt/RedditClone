package com.adikmt.repositories

import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.SubredditRequest
import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.orm.SubredditEntity
import com.adikmt.orm.SubredditFollowerEntity
import com.adikmt.orm.helperfuncs.fromResultRowSubreddit
import com.adikmt.orm.helperfuncs.toSubredditResponseList
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.resultOf
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteIgnoreWhere
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.select

/** Subreddit repository */
interface SubredditRepository {
    /**
     * Add subreddit
     *
     * @param userName
     * @param subredditRequest
     * @return
     */
    suspend fun addSubreddit(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse?>

    /**
     * Get subreddit by name
     *
     * @param subredditName
     * @return
     */
    suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?>

    /**
     * Search subreddit by name
     *
     * @param subredditName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun searchSubredditByName(
        subredditName: SubredditName,
        limit: Int,
        offset: Long
    ): Result<SubredditResponseList>

    /**
     * Follow subreddit
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    /**
     * Unfollow subreddit
     *
     * @param userName
     * @param subredditName
     * @return
     */
    suspend fun unfollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    /**
     * Is subreddit followed
     *
     * @param subredditName
     * @param userName
     * @return
     */
    suspend fun isSubredditFollowed(subredditName: SubredditName, userName: UserName): Result<Boolean>

    /**
     * Get all followed subreddits
     *
     * @param userName
     * @param limit
     * @param offset
     * @return
     */
    suspend fun getAllFollowedSubreddits(userName: UserName, limit: Int, offset: Long): Result<SubredditResponseList>
}

/**
 * Subreddit repo impl
 *
 * @constructor Create empty Subreddit repo impl
 * @property dbTransaction
 */
class SubredditRepoImpl(private val dbTransaction: DbTransaction) : SubredditRepository {
    override suspend fun addSubreddit(
        userName: UserName,
        subredditRequest: SubredditRequest
    ): Result<SubredditResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = SubredditEntity.insertIgnoreAndGetId {
                    it[SubredditEntity.title] = subredditRequest.subredditName
                    it[SubredditEntity.desc] = subredditRequest.subredditDesc
                    it[SubredditEntity.createdByUser] = userName.value
                }
                id?.let {
                    SubredditResponse(
                        id = it.value,
                        subredditName = subredditRequest.subredditName,
                        subredditDesc = subredditRequest.subredditDesc,
                        createdByUser = userName
                    )
                }
            }
        }
    }

    override suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditEntity.select {
                    SubredditEntity.title eq subredditName.value
                }.map {
                    it.fromResultRowSubreddit()
                }.firstOrNull()
            }
        }
    }

    override suspend fun searchSubredditByName(
        subredditName: SubredditName,
        limit: Int,
        offset: Long
    ): Result<SubredditResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditEntity.select {
                    SubredditEntity.title like subredditName.value
                }
                    .limit(n = limit, offset = offset)
                    .map {
                        it.fromResultRowSubreddit()
                    }.toSubredditResponseList(limit, offset)
            }
        }
    }

    override suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity.insertIgnore {
                    it[SubredditFollowerEntity.subredditId] = subredditName.value
                    it[SubredditFollowerEntity.userId] = userName.value
                }
                subredditName
            }
        }
    }

    override suspend fun unfollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity.deleteIgnoreWhere {
                    SubredditFollowerEntity.userId eq userName.value and (SubredditFollowerEntity.subredditId eq subredditName.value)
                }
                subredditName
            }
        }
    }

    override suspend fun isSubredditFollowed(subredditName: SubredditName, userName: UserName): Result<Boolean> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity.select {
                    SubredditFollowerEntity.userId eq userName.value and (SubredditFollowerEntity.subredditId eq subredditName.value)
                }.empty().not()
            }
        }
    }

    override suspend fun getAllFollowedSubreddits(
        userName: UserName,
        limit: Int,
        offset: Long
    ): Result<SubredditResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity
                    .innerJoin(SubredditEntity, { SubredditFollowerEntity.subredditId }, { SubredditEntity.title })
                    .slice(listOf(SubredditFollowerEntity.userId) + SubredditEntity.columns)
                    .select {
                        SubredditFollowerEntity.userId eq userName.value
                    }.map {
                        it.fromResultRowSubreddit()
                    }.toSubredditResponseList(limit, offset)
            }
        }
    }

}