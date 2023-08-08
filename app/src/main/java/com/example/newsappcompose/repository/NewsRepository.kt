package com.example.newsappcompose.repository

import com.example.newsappcompose.api.NewsApi
import com.example.newsappcompose.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    fun getNews(countryCode: String) =
        flow { emit(newsApi.getNews(countryCode)) }.flowOn(Dispatchers.IO)
}
