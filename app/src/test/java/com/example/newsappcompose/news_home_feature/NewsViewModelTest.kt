package com.example.newsappcompose.news_home_feature

import com.example.newsappcompose.base.MainCoroutineExt
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.news_home_feature.data.remote.dto.SourceDto
import com.example.newsappcompose.news_home_feature.domain.model.Article
import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
import com.example.newsappcompose.news_home_feature.domain.repository.NewsRepository
import com.example.newsappcompose.news_home_feature.presentation.viewmodel.NewsViewModel
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
            author = "John Doe",
            content = "Lorem ipsum dolor sit amet",
            description = "Lorem ipsum dolor sit amet",
            publishedAt = "2023-10-15T12:00:00Z",
            source = SourceDto("1","CNN"),
            title = "Sample Article",
            url = "https://www.example.com/article/1",
            urlToImage = "https://www.example.com/article/1/image.jpg"
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