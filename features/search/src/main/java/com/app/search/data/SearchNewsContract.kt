package com.app.search.data

import com.app.search.remote.model.NewsDataSuccessResponse
import com.app.search.remote.model.NewsSourceSuccessResponse

/**
 * Created by {EUNICE BAKARE T.} on {3/26/24}
 * Email: {eunice@reach.africa}
 */

interface SearchNewsContract {
    suspend fun searchNews(query: String, sortBy: String?): NewsDataSuccessResponse

    suspend fun getSources(): NewsSourceSuccessResponse
}