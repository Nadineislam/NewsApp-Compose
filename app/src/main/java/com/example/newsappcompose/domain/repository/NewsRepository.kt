package com.example.newsappcompose.domain.repository

import com.example.newsappcompose.domain.model.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(countryCode: String): Response<NewsResponse>
}