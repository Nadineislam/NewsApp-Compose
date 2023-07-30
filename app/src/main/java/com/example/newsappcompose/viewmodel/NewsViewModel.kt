package com.example.newsappcompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.model.NewsResponse
import com.example.newsappcompose.repository.NewsRepository
import com.example.newsappcompose.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    init {

        getNews("us")
    }


    private fun getNews(countryCode: String) = viewModelScope.launch {
        news.postValue(Resource.Loading())
        val response = newsRepository.getNews(countryCode)
        news.postValue(handleNews(response))
        Log.e("Hi", response.toString())

    }

    private fun handleNews(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun observeNewsLiveData(): LiveData<Resource<NewsResponse>> = news

}