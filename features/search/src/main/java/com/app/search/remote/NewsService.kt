package com.app.search.remote

import com.app.search.data.SearchNewsContract
import com.app.search.remote.model.NewsDataSuccessResponse
import com.app.search.remote.model.NewsSourceSuccessResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by {EUNICE BAKARE T.} on {1/1/20}
 * Email: {eunice@reach.africa}
 */

const val BASE_URL = "https://newsapi.org/v2/"

interface NewsService {

    @GET(value = "everything?language=en")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String?,
    ): NewsDataSuccessResponse

    @GET(value = "top-headlines/sources?language=en")
    suspend fun getSources(): NewsSourceSuccessResponse
}


class NewsContractImpl(
    okhttpCallFactory: Lazy<Call.Factory>,
): SearchNewsContract {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.value.newCall(it) }
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory()).build()
            ).asLenient())
            .build()
            .create(NewsService::class.java)

    override suspend fun searchNews(query: String, sortBy: String?): NewsDataSuccessResponse {
        return networkApi.searchNews(query, sortBy)
    }

    override suspend fun getSources(): NewsSourceSuccessResponse {
        return networkApi.getSources()
    }

}