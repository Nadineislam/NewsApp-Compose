package com.example.newsappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.data.model.NewsResponse
import com.example.newsappcompose.data.repository.NewsRepository
import com.example.newsappcompose.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _news: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val news: StateFlow<Resource<NewsResponse>> = _news

    init {
        getNews()
    }
    private fun getNews() = viewModelScope.launch {
        _news.value = Resource.Loading()
        val response = newsRepository.getNews("us")
        _news.value = handleNewsResponse(response)
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