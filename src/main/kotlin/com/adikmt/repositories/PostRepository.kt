package com.adikmt.repositories

import com.adikmt.dtos.PostHeading
import com.adikmt.dtos.PostId
import com.adikmt.dtos.PostRequest
import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.SubredditName
import com.adikmt.dtos.UserName
import com.adikmt.orm.PostEntity
import com.adikmt.orm.PostFavouriteEntity
import com.adikmt.orm.helperfuncs.fromResultRowPost
import com.adikmt.orm.helperfuncs.toPostResponseList
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.resultOf
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteIgnoreWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.select

interface PostRepository {
    suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse>

    suspend fun getPostById(postId: PostId): Result<PostResponse?>

    suspend fun getPostFeed(userName: UserName?): Result<PostResponseList>

    suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList>

    suspend fun getPostListBySubredditName(subredditName: SubredditName): Result<PostResponseList>

    suspend fun getPostListByUser(userName: UserName): Result<PostResponseList>

    suspend fun upvotePost(userName: UserName, postId: PostId): Result<PostId>

    suspend fun downvotePost(userName: UserName, postId: PostId): Result<PostId>
}

class PostRepoImpl(private val dbTransaction: DbTransaction) : PostRepository {
    override suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = PostEntity.insertIgnoreAndGetId {
                    it[PostEntity.author] = userName.value
                    it[PostEntity.title] = postRequest.postHeading
                    it[PostEntity.desc] = postRequest.postBody
                    it[PostEntity.subreddit] = postRequest.subredditName
                }

                PostResponse(
                    postId = id?.value,
                    subredditName = postRequest.subredditName,
                    postHeading = postRequest.postHeading,
                    postBody = postRequest.postBody,
                    postAuthor = userName,
                    postCreatedAt = CurrentDateTime.toString(),
                    noOfUpvotes = 0
                )
            }
        }
    }

    override suspend fun getPostById(postId: PostId): Result<PostResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                PostEntity.select {
                    PostEntity.id eq postId.value.toLong()
                }.map {
                    it.fromResultRowPost(0) //Temporary
                }.firstOrNull()
            }
        }
    }

    override suspend fun getPostFeed(userName: UserName?): Result<PostResponseList> {
        TODO("Not yet implemented")
    }

    override suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                PostEntity.select {
                    PostEntity.title like postHeading.value
                }.map {
                    it.fromResultRowPost(0) //Temporary
                }.toPostResponseList()
            }
        }
    }

    override suspend fun getPostListBySubredditName(subredditName: SubredditName): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                PostEntity.select {
                    PostEntity.subreddit eq subredditName.value
                }.map {
                    it.fromResultRowPost(0) //Temporary
                }.toPostResponseList()
            }
        }
    }

    override suspend fun getPostListByUser(userName: UserName): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                PostEntity.select {
                    PostEntity.author eq userName.value
                }.map {
                    it.fromResultRowPost(0) //Temporary
                }.toPostResponseList()
            }
        }
    }

    override suspend fun upvotePost(userName: UserName, postId: PostId): Result<PostId> {
        return dbTransaction.dbQuery {
            resultOf {
                PostFavouriteEntity.insertIgnore {
                    it[PostFavouriteEntity.postId] = postId.value.toLong()
                    it[PostFavouriteEntity.userId] = userName.value
                }

                postId
            }
        }
    }

    override suspend fun downvotePost(userName: UserName, postId: PostId): Result<PostId> {
        return dbTransaction.dbQuery {
            resultOf {
                PostFavouriteEntity.deleteIgnoreWhere {
                    PostFavouriteEntity.postId eq postId.value.toLong() and (PostFavouriteEntity.userId eq userName.value)
                }

                postId
            }
        }
    }

}