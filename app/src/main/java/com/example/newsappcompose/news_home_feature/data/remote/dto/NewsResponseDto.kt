package com.example.newsappcompose.news_home_feature.data.remote.dto


data class NewsResponseDto(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)