package com.adikmt.repositories

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentRequest
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.PostId
import com.adikmt.dtos.UserName
import com.adikmt.orm.CommentEntity
import com.adikmt.orm.CommentFavouriteEntity
import com.adikmt.orm.helperfuncs.FromResultRowCommentWithUpvote
import com.adikmt.orm.helperfuncs.toCommentResponseList
import com.adikmt.utils.db.DbTransaction
import com.adikmt.utils.resultOf
import org.jetbrains.exposed.sql.Count
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteIgnoreWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.select

interface CommentRepository {
    suspend fun addComment(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?>

    suspend fun getCommentById(commentId: CommentId): Result<CommentResponse?>

    suspend fun getCommentListByPostId(postId: PostId): Result<CommentResponseList>

    suspend fun getCommentListByUserName(userName: UserName): Result<CommentResponseList>

    suspend fun upvoteComment(commentId: CommentId, userName: UserName): Result<CommentId>

    suspend fun downvoteComment(commentId: CommentId, userName: UserName): Result<CommentId>
}

class CommentRepositoryImpl(private val dbTransaction: DbTransaction) : CommentRepository {
    override suspend fun addComment(userName: UserName, commentRequest: CommentRequest): Result<CommentResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                val id = CommentEntity.insertIgnoreAndGetId {
                    it[CommentEntity.post] = commentRequest.postId.toLong()
                    it[CommentEntity.author] = userName.value
                    it[CommentEntity.text] = commentRequest.commentBody
                    it[CommentEntity.parentComment] = commentRequest.parentComment
                }

                id?.let {
                    CommentResponse(
                        commentId = it.value,
                        commentBody = commentRequest.commentBody,
                        commentAuthor = userName,
                        parentComment = commentRequest.parentComment,
                        createdAt = CurrentDateTime.toString(),
                        upvoteNo = 0
                    )
                }
            }
        }
    }

    override suspend fun getCommentById(commentId: CommentId): Result<CommentResponse?> {
        return dbTransaction.dbQuery {
            resultOf {
                (CommentEntity.leftJoin(
                    CommentFavouriteEntity
                ))
                    .slice(
                        CommentEntity.id, CommentEntity.text,
                        CommentEntity.author, CommentEntity.parentComment,
                        CommentEntity.createdAt,
                        Count(CommentFavouriteEntity.commentId).alias("likes")
                    )
                    .select { CommentEntity.id eq commentId.value.toLong() }
                    .groupBy(CommentEntity.id)
                    .orderBy(CommentFavouriteEntity.commentId, SortOrder.DESC).map { row ->
                        row.FromResultRowCommentWithUpvote()
                    }.firstOrNull()
            }
        }
    }

    override suspend fun getCommentListByPostId(postId: PostId): Result<CommentResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (CommentEntity.leftJoin(
                    CommentFavouriteEntity
                ))
                    .slice(
                        CommentEntity.id, CommentEntity.text,
                        CommentEntity.author, CommentEntity.parentComment,
                        CommentEntity.createdAt,
                        Count(CommentFavouriteEntity.commentId).alias("likes")
                    )
                    .select { CommentEntity.post eq postId.value.toLong() }
                    .groupBy(CommentEntity.id)
                    .orderBy(CommentFavouriteEntity.commentId, SortOrder.DESC).map { row ->
                        row.FromResultRowCommentWithUpvote()
                    }.toCommentResponseList()
            }
        }
    }

    override suspend fun getCommentListByUserName(userName: UserName): Result<CommentResponseList> {
        return dbTransaction.dbQuery {
            resultOf {
                (CommentEntity.leftJoin(
                    CommentFavouriteEntity
                ))
                    .slice(
                        CommentEntity.id, CommentEntity.text,
                        CommentEntity.author, CommentEntity.parentComment,
                        CommentEntity.createdAt,
                        Count(CommentFavouriteEntity.commentId).alias("likes")
                    )
                    .select { CommentEntity.author eq userName.value }
                    .groupBy(CommentEntity.id)
                    .orderBy(CommentFavouriteEntity.commentId, SortOrder.DESC).map { row ->
                        row.FromResultRowCommentWithUpvote()
                    }.toCommentResponseList()
            }
        }
    }

    override suspend fun upvoteComment(commentId: CommentId, userName: UserName): Result<CommentId> {
        return dbTransaction.dbQuery {
            resultOf {
                CommentFavouriteEntity.insertIgnore {
                    it[CommentFavouriteEntity.commentId] = commentId.value.toLong()
                    it[CommentFavouriteEntity.userId] = userName.value
                }

                commentId
            }
        }
    }

    override suspend fun downvoteComment(commentId: CommentId, userName: UserName): Result<CommentId> {
        return dbTransaction.dbQuery {
            resultOf {
                CommentFavouriteEntity.deleteIgnoreWhere {
                    CommentFavouriteEntity.commentId eq commentId.value.toLong() and (CommentFavouriteEntity.userId eq userName.value)
                }

                commentId
            }
        }
    }

}