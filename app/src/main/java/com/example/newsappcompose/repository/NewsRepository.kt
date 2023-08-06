package com.example.newsappcompose.repository

import com.example.newsappcompose.api.NewsApi

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(countryCode: String) =
        newsApi.getNews(countryCode)
}