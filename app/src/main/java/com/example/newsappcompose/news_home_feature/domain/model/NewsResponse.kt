package com.example.newsappcompose.news_home_feature.domain.model


data class NewsResponse(val articles: List<Article>,
                        val status: String,
                        val totalResults: Int)
