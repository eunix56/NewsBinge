package com.app.search.remote.model

/**
 * Created by {EUNICE BAKARE T.} on {4/15/24}
 * Email: {eunice@reach.africa}
 */


class NewsSourceSuccessResponse(
    val status: String,
    val source: List<NewsSourceModel>
)
class NewsSourceModel(
    val id: String,
    val name: String,
    val description: String,
    val category: String
)