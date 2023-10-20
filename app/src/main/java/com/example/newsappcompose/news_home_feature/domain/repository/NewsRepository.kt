package com.example.newsappcompose.news_home_feature.domain.repository

import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(countryCode: String): Response<NewsResponse>
}