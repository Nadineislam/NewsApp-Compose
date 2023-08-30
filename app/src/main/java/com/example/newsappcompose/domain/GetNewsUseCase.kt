package com.example.newsappcompose.domain

import com.example.newsappcompose.data.model.NewsResponse
import com.example.newsappcompose.data.repository.NewsRepository
import com.example.newsappcompose.data.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(): Resource<NewsResponse> {
        val response = newsRepository.getNews("us")
        return handleNewsResponse(response)

    }
    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}




