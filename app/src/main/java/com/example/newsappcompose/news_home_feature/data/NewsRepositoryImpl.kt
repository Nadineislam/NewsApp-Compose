package com.example.newsappcompose.news_home_feature.data

import com.example.newsappcompose.news_home_feature.data.remote.NewsApi
import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
import com.example.newsappcompose.news_home_feature.domain.repository.NewsRepository
import retrofit2.Response

import javax.inject.Inject
class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi): NewsRepository {
    override suspend fun getNews(countryCode: String): Response<NewsResponse> =
       newsApi.getNews(countryCode)}


