package com.adikmt.orm.helperfuncs

import com.adikmt.dtos.CommentId
import com.adikmt.dtos.CommentResponse
import com.adikmt.dtos.CommentResponseList
import com.adikmt.dtos.UserName
import com.adikmt.orm.CommentEntity
import com.adikmt.orm.CommentFavouriteEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.count

fun ResultRow.FromResultRowCommentWithUpvote() = CommentResponse(
    commentId = this[CommentEntity.id].value,
    commentBody = this[CommentEntity.text],
    commentAuthor = UserName(this[CommentEntity.author]),
    parentComment = this[CommentEntity.parentComment]?.value,
    createdAt = this[CommentEntity.createdAt].toString(),
    upvoteNo = this[CommentFavouriteEntity.commentId.count()],
)

fun ResultRow.toCommentIds() = CommentId(
    value = this[CommentEntity.id].value.toString()
)

fun List<CommentResponse>.toCommentResponseList() = CommentResponseList(
    commentList = this,
    commentNo = this.size
)