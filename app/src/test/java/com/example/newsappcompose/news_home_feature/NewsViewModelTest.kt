package com.example.newsappcompose.news_home_feature

import com.example.newsappcompose.base.MainCoroutineExt
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.news_home_feature.data.remote.dto.SourceDto
import com.example.newsappcompose.news_home_feature.domain.model.Article
import com.example.newsappcompose.news_home_feature.presentation.viewmodel.NewsViewModel
import com.example.newsappcompose.util.expectedArticle
import com.example.newsappcompose.util.fakeErrorNewsRepository
import com.example.newsappcompose.util.fakeSuccessNewsRepository
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
    fun `when getNews is called then error should be retrieved`() = runTest {

        val fakeNewsUseCase = FakeNewsUseCase(fakeErrorNewsRepository)
        viewModel = NewsViewModel(fakeNewsUseCase)

        viewModel.getNews()

        val expectedErrorMessage = "An error occurred"
        val actualResource = viewModel.news.value

        val actualErrorMessage = (actualResource as Resource.Error).message
        assertEquals(expectedErrorMessage, actualErrorMessage)
    }
}