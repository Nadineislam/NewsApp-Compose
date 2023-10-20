package com.example.newsappcompose.news_home_feature

import com.example.newsappcompose.base.MainCoroutineExt
import com.example.newsappcompose.news_home_feature.presentation.viewmodel.NewsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {
    @get:Rule
    val mainCoroutineExt = MainCoroutineExt()
    private lateinit var viewModel: NewsViewModel
    private lateinit var fakeGetNews: FakeNewsUseCase

    @Before
    fun setup() {
        fakeGetNews = FakeNewsUseCase(fakeSuccessNewsRepository)
        viewModel = NewsViewModel(fakeGetNews)
    }

    @Test
    fun `when getNews is called with success state then articles should be retrieved`() = runTest {
        viewModel.getNews()
        assertEquals(expectedArticle, viewModel.news.value.data?.articles)
    }

    @Test
    fun `when getNews is called with failure state then error should be retrieved`() = runTest {
        val fakeNewsUseCase = FakeNewsUseCase(fakeErrorNewsRepository)
        viewModel = NewsViewModel(fakeNewsUseCase)

        viewModel.getNews()

        assertEquals("An error occurred", viewModel.news.value.message)
    }
}