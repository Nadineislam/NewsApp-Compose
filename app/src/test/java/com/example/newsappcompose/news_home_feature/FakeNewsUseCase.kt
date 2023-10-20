package com.example.newsappcompose.news_home_feature

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
import com.example.newsappcompose.news_home_feature.domain.repository.NewsRepository
import com.example.newsappcompose.news_home_feature.domain.use_case.NewsUseCase
import retrofit2.Response
import javax.inject.Inject

class FakeNewsUseCase @Inject constructor(private val repository: NewsRepository): NewsUseCase(repository) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend operator fun invoke(countryCode: String): Response<NewsResponse> {
        return repository.getNews(countryCode)
    }
}