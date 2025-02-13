package com.app.search.remote.model

import com.app.common.model.NewsItemData
import com.app.common.model.SourceData
import com.app.search.remote.model.Source.Companion.toSourceData

/**
 * Created by {EUNICE BAKARE T.} on {3/25/24}
 * Email: {eunice@reach.africa}
 */

class NewsDataSuccessResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsData>
)

class NewsData(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) {
    companion object {
        fun NewsData.toNewsItemData(): NewsItemData =
            NewsItemData(
                title, description,urlToImage, "", author,
                null, publishedAt, source.toSourceData(), content, url
            )
    }
}

class Source(
    val id: String,
    val name: String
) {
    companion object {
        fun Source.toSourceData(): SourceData =
            SourceData(id, name)
    }
}