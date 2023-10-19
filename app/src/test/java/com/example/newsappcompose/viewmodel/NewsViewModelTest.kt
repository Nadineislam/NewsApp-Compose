package com.example.newsappcompose.viewmodel

import com.example.newsappcompose.FakeNewsRepository
import com.example.newsappcompose.base.MainCoroutineExt
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.domain.model.Article
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import com.example.newsappcompose.presentation.viewmodel.NewsViewModel
import com.example.newsappcompose.use_case.FakeNewsUseCase
import com.example.newsappcompose.util.expectedAuthor
import com.example.newsappcompose.util.expectedContent
import com.example.newsappcompose.util.expectedDescription
import com.example.newsappcompose.util.expectedPublishedAt
import com.example.newsappcompose.util.expectedSource
import com.example.newsappcompose.util.expectedTitle
import com.example.newsappcompose.util.expectedUrl
import com.example.newsappcompose.util.expectedUrlToImage
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class NewsViewModelTest {
    @get:Rule
    val mainCoroutineExt = MainCoroutineExt()
    private lateinit var viewModel: NewsViewModel
    private lateinit var fakeGetNews: FakeNewsUseCase
    private lateinit var fakeNewsRepository: FakeNewsRepository

    @Before
    fun setup() {
        fakeNewsRepository = FakeNewsRepository()
        fakeGetNews = FakeNewsUseCase(fakeNewsRepository)
        viewModel = NewsViewModel(fakeGetNews)
    }

    @Test
    fun `when getNews is called then articles should be retrieved`() = runTest {
        val expectedArticle = Article(
            author = expectedAuthor,
            content = expectedContent,
            description = expectedDescription,
            publishedAt = expectedPublishedAt,
            source = expectedSource,
            title = expectedTitle,
            url = expectedUrl,
            urlToImage = expectedUrlToImage
        )

        val actualResource = viewModel.news.value
        val actualNewsResponse = actualResource.data

        viewModel.getNews()

        val actualArticles = actualNewsResponse!!.articles
        val actualArticle = actualArticles.first()

        assertEquals(expectedArticle, actualArticle)
    }

    @Test
    fun `when getNews is called then error should be retrieved`() = runTest {
        val fakeRepository = object : NewsRepository {
            override suspend fun getNews(countryCode: String): Response<NewsResponse> {
                return Response.error(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }
        val fakeNewsUseCase = FakeNewsUseCase(fakeRepository)
        viewModel = NewsViewModel(fakeNewsUseCase)

        viewModel.getNews()

        val expectedErrorMessage = "An error occurred"
        val actualResource = viewModel.news.value

        val actualErrorMessage = (actualResource as Resource.Error).message
        assertEquals(expectedErrorMessage, actualErrorMessage)
    }
}