package com.example.newsappcompose.news_home_feature.data.remote

import com.example.newsappcompose.core.utils.Constants.Constants.Companion.API_KEY
import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
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
