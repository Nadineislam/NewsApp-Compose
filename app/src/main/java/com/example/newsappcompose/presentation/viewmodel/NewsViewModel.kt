package com.example.newsappcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.domain.usecase.NewsUseCase
import com.example.newsappcompose.data.model.NewsResponse
import com.example.newsappcompose.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : ViewModel() {
    private val _news: MutableStateFlow<Resource<NewsResponse>> =
        MutableStateFlow(Resource.Loading())
    val news: StateFlow<Resource<NewsResponse>> = _news

    init {
        getNews()
    }
    private fun getNews() = viewModelScope.launch {
        _news.value = Resource.Loading()
        val response = newsUseCase()
        _news.value = response
    }
}