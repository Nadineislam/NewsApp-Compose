package com.example.newsappcompose.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.use_case.GetNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class NewsViewModel @Inject constructor(private val getNews: GetNews) : ViewModel() {
    private val _news: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val news: StateFlow<Resource<NewsResponse>> = _news

    init {
        getNews()
    }
     fun getNews() = viewModelScope.launch {
        _news.value = Resource.Loading()
        val response = getNews("us")
        _news.value = handleNewsResponse(response)
    }
    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("An error occurred")
    }
}