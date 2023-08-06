package com.example.newsappcompose.api

import com.example.newsappcompose.utils.Constants.Constants.Companion.API_KEY
import com.example.newsappcompose.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}
