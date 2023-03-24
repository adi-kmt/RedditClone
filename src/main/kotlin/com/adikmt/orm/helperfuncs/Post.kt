package com.adikmt.orm.helperfuncs

import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.UserName
import com.adikmt.orm.PostEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.fromResultRowPost(upvoteNo: Int) = PostResponse(
    postId = this[PostEntity.id].value,
    postHeading = this[PostEntity.title],
    postAuthor = UserName(this[PostEntity.author]),
    postCreatedAt = this[PostEntity.createdAt].toString(),
    subredditName = this[PostEntity.subreddit],
    postBody = this[PostEntity.desc].orEmpty(),
    noOfUpvotes = upvoteNo
)

fun List<PostResponse>.toPostResponseList() = PostResponseList(
    postList = this,
    postNo = this.size
)