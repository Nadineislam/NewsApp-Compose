package com.example.newsappcompose.data.repository

import com.example.newsappcompose.data.api.NewsApi
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    fun getNews(countryCode: String) =
        flow { emit(newsApi.getNews(countryCode)) }
}
