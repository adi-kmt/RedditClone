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
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

interface SubredditRepository {
    suspend fun addSubreddit(userName: UserName, subredditRequest: SubredditRequest): Result<SubredditResponse>

    suspend fun getSubredditByName(subredditName: SubredditName): Result<SubredditResponse?>

    suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList>

    suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    suspend fun unfollowSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName>

    suspend fun isSubredditFollowed(subredditName: SubredditName, userName: UserName): Result<Boolean>

    suspend fun getAllFollowedSubreddits(userName: UserName): Result<SubredditResponseList>
}

class SubredditRepoImpl(private val dbTransaction: DbTransaction) : SubredditRepository {
    override suspend fun addSubreddit(
        userName: UserName,
        subredditRequest: SubredditRequest
    ): Result<SubredditResponse> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = SubredditEntity.insertAndGetId {
                    it[SubredditEntity.title] = subredditRequest.subredditName
                    it[SubredditEntity.desc] = subredditRequest.subredditDesc
                    it[SubredditEntity.createdByUser] = userName.value
                }
                SubredditResponse(
                    id = id.value,
                    subredditName = subredditRequest.subredditName,
                    subredditDesc = subredditRequest.subredditDesc,
                    createdByUser = userName
                )
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

    override suspend fun searchSubredditByName(subredditName: SubredditName): Result<SubredditResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditEntity.select {
                    SubredditEntity.title like subredditName.value
                }.map {
                    it.fromResultRowSubreddit()
                }.distinct().toSubredditResponseList()
            }
        }
    }

    override suspend fun followSubreddit(userName: UserName, subredditName: SubredditName): Result<SubredditName> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity.insert {
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
                SubredditFollowerEntity.deleteWhere {
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

    override suspend fun getAllFollowedSubreddits(userName: UserName): Result<SubredditResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                SubredditFollowerEntity
                    .innerJoin(SubredditEntity, { SubredditFollowerEntity.subredditId }, { SubredditEntity.title })
                    .slice(listOf(SubredditFollowerEntity.userId) + SubredditEntity.columns)
                    .select {
                        SubredditFollowerEntity.userId eq userName.value
                    }.distinct().map {
                        it.fromResultRowSubreddit()
                    }.toSubredditResponseList()
            }
        }
    }

}