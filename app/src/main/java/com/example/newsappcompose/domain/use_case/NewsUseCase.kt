package com.example.newsappcompose.domain.use_case

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class NewsUseCase @Inject constructor(private val repository: NewsRepository){
   @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
   open suspend operator fun invoke(countryCode: String):Response<NewsResponse>{
        return repository.getNews(countryCode)
    }
}