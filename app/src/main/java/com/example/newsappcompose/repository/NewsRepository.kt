package com.example.newsappcompose.repository

import com.example.newsappcompose.api.RetrofitInstance

class NewsRepository {
    suspend fun getNews(countryCode: String) =
        RetrofitInstance.api.getNews(countryCode)
}