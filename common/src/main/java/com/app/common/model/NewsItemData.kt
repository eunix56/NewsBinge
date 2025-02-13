package com.app.common.model

/**
 * Created by {EUNICE BAKARE T.} on {3/9/24}
 * Email: {eunice@reach.africa}
 */

class NewsItemData(
    val title: String,
    val description: String,
    val image: String,
    var category: String = "World",
    val author: String,
    val authorImg: String?,
    val datePosted: String,
    val source: SourceData,
    val content: String,
    val url: String
)

class SourceData(
    val id: String,
    val name: String
)