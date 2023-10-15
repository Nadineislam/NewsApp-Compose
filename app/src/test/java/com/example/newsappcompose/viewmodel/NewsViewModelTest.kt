package com.example.newsappcompose.viewmodel

import com.example.newsappcompose.FakeNewsRepository
import com.example.newsappcompose.base.MainCoroutineExt
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import com.example.newsappcompose.presentation.viewmodel.NewsViewModel
import com.example.newsappcompose.use_case.FakeGetNews
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
    val mainCoroutineExt=MainCoroutineExt()
    private lateinit var viewModel: NewsViewModel
    private lateinit var fakeGetNews:FakeGetNews
    private lateinit var fakeNewsRepository: FakeNewsRepository
    @Before
    fun setup(){
        fakeNewsRepository= FakeNewsRepository()
        fakeGetNews=FakeGetNews(fakeNewsRepository)
        viewModel = NewsViewModel(fakeGetNews )
    }

@Test
fun `test getNews success`() = runTest {
    viewModel.getNews()

    val expectedSize = 1
    val actualResource = viewModel.news.value
    val actualNewsResponse = actualResource.data

    assertNotNull(actualNewsResponse)
    assertEquals(expectedSize, actualNewsResponse!!.articles.size)

    for (article in actualNewsResponse.articles) {
        assertNotNull(article.author)
        assertNotNull(article.content)
        assertNotNull(article.description)
        assertNotNull(article.publishedAt)
        assertNotNull(article.source)
        assertNotNull(article.title)
        assertNotNull(article.url)
        assertNotNull(article.urlToImage)
    }
}
    @Test
    fun `test getNews error`() = runTest {
        val fakeRepository = object : NewsRepository {
            override suspend fun getNews(countryCode: String): Response<NewsResponse> {
                return Response.error(
                    500,
                    "Error".toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }
        val fakeGetNews = FakeGetNews(fakeRepository)
        viewModel = NewsViewModel(fakeGetNews)

        viewModel.getNews()

        val expectedErrorMessage = "An error occurred"
        val actualResource = viewModel.news.value

        val actualErrorMessage = (actualResource as Resource.Error).message
        assertEquals(expectedErrorMessage, actualErrorMessage)
    }
}