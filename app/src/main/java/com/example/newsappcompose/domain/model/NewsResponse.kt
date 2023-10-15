package com.example.newsappcompose.domain.model


data class NewsResponse(val articles: List<Article>,
                        val status: String,
                        val totalResults: Int)
