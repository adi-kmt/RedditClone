package com.adikmt.orm.helperfuncs

import com.adikmt.dtos.PostResponse
import com.adikmt.dtos.PostResponseList
import com.adikmt.dtos.UserName
import com.adikmt.orm.PostEntity
import com.adikmt.orm.PostFavouriteEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.count

fun ResultRow.fromResultRowPost() = PostResponse(
    postId = this[PostEntity.id].value,
    postHeading = this[PostEntity.title],
    postAuthor = UserName(this[PostEntity.author]),
    postCreatedAt = this[PostEntity.createdAt].toString(),
    subredditName = this[PostEntity.subreddit],
    postBody = this[PostEntity.desc].orEmpty(),
    noOfUpvotes = this[PostFavouriteEntity.postId.count()]
)

fun List<PostResponse>.toPostResponseList() = PostResponseList(
    postList = this,
    postNo = this.size
)