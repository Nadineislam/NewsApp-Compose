package com.example.newsappcompose.use_case

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import com.example.newsappcompose.domain.use_case.NewsUseCase
import retrofit2.Response
import javax.inject.Inject

class FakeNewsUseCase @Inject constructor(private val repository: NewsRepository):NewsUseCase(repository) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend operator fun invoke(countryCode: String): Response<NewsResponse> {
        return repository.getNews(countryCode)
    }
}