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
import com.adikmt.orm.UserFollowersEntity
import com.adikmt.orm.helperfuncs.fromResultRowPost
import com.adikmt.orm.helperfuncs.toPostResponseList
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.resultOf
import org.jetbrains.exposed.sql.Count
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteIgnoreWhere
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

/**
 * Post repository
 *
 */
interface PostRepository {
    /**
     * Add post
     *
     * @param userName
     * @param postRequest
     * @return
     */
    suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse?>

    /**
     * Get post by id
     *
     * @param postId
     * @return
     */
    suspend fun getPostById(postId: PostId): Result<PostResponse?>

    /**
     * Get post feed
     *
     * @param userName
     * @return
     */
    suspend fun getPostFeed(userName: UserName?): Result<PostResponseList>

    /**
     * Search post by heading
     *
     * @param postHeading
     * @return
     */
    suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList>

    /**
     * Get post list by subreddit name
     *
     * @param subredditName
     * @return
     */
    suspend fun getPostListBySubredditName(subredditName: SubredditName): Result<PostResponseList>

    /**
     * Get post list by user
     *
     * @param userName
     * @return
     */
    suspend fun getPostListByUser(userName: UserName): Result<PostResponseList>

    /**
     * Upvote post
     *
     * @param userName
     * @param postId
     * @return
     */
    suspend fun upvotePost(userName: UserName, postId: PostId): Result<PostId>

    /**
     * Downvote post
     *
     * @param userName
     * @param postId
     * @return
     */
    suspend fun downvotePost(userName: UserName, postId: PostId): Result<PostId>
}

/**
 * Post repo impl
 *
 * @constructor Create empty Post repo impl
 * @property dbTransaction
 */
class PostRepoImpl(private val dbTransaction: DbTransaction) : PostRepository {
    override suspend fun addPost(userName: UserName, postRequest: PostRequest): Result<PostResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = PostEntity.insertIgnoreAndGetId {
                    it[PostEntity.author] = userName.value
                    it[PostEntity.title] = postRequest.postHeading
                    it[PostEntity.desc] = postRequest.postBody
                    it[PostEntity.subreddit] = postRequest.subredditName
                }

                id?.let {
                    PostResponse(
                        postId = it.value,
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
    }

    override suspend fun getPostById(postId: PostId): Result<PostResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                ))
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .select { PostEntity.id eq postId.value.toLong() }
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
                    }.firstOrNull()
            }
        }
    }

    override suspend fun getPostFeed(userName: UserName?): Result<PostResponseList> {
        userName?.let {
            return getSignedInUserFeed(it)
        } ?: return getGuestUserFeed()
    }

    private suspend fun getGuestUserFeed(): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                ))
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .selectAll()
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
                    }.toPostResponseList()
            }
        }
    }

    private suspend fun getSignedInUserFeed(userName: UserName): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                )).innerJoin(UserFollowersEntity, { PostEntity.author }, { UserFollowersEntity.followeeId })
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .selectAll()
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
                    }.toPostResponseList()
            }
        }
    }

    override suspend fun searchPostByHeading(postHeading: PostHeading): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                ))
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .select { PostEntity.title like postHeading.value }
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
                    }.toPostResponseList()
            }
        }
    }

    override suspend fun getPostListBySubredditName(subredditName: SubredditName): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                ))
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .select { PostEntity.subreddit eq subredditName.value }
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
                    }.toPostResponseList()
            }
        }
    }

    override suspend fun getPostListByUser(userName: UserName): Result<PostResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (PostEntity.leftJoin(
                    PostFavouriteEntity
                ))
                    .slice(
                        PostEntity.id, PostEntity.title,
                        PostEntity.author, PostEntity.desc,
                        PostEntity.subreddit,
                        PostEntity.createdAt,
                        Count(PostFavouriteEntity.postId).alias("likes")
                    )
                    .select { PostEntity.author eq userName.value }
                    .groupBy(PostEntity.id)
                    .orderBy(PostFavouriteEntity.postId, SortOrder.DESC).map { row ->
                        row.fromResultRowPost()
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
