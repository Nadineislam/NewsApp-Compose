package com.example.newsappcompose.data

import com.example.newsappcompose.data.remote.NewsApi
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import retrofit2.Response

import javax.inject.Inject
class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi):NewsRepository {
    override suspend fun getNews(countryCode: String): Response<NewsResponse> =
       newsApi.getNews(countryCode)}


