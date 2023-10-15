package com.example.newsappcompose.data.remote

import com.example.newsappcompose.core.utils.Constants.Constants.Companion.API_KEY
import com.example.newsappcompose.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}
