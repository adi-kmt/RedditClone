package com.adikmt.orm.helperfuncs

import com.adikmt.dtos.SubredditResponse
import com.adikmt.dtos.SubredditResponseList
import com.adikmt.dtos.UserName
import com.adikmt.orm.SubredditEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.fromResultRowSubreddit() = SubredditResponse(
    id = this[SubredditEntity.id].value,
    subredditName = this[SubredditEntity.title],
    subredditDesc = this[SubredditEntity.desc],
    createdByUser = UserName(value = this[SubredditEntity.createdByUser])
)

fun List<SubredditResponse>.toSubredditResponseList(limit: Int, offset: Long) = SubredditResponseList(
    subredditList = this,
    subredditNo = this.size,
    limit = limit,
    offset = offset
)