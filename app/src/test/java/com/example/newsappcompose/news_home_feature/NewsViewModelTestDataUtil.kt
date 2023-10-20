package com.example.newsappcompose.news_home_feature

import com.example.newsappcompose.news_home_feature.data.remote.dto.SourceDto
import com.example.newsappcompose.news_home_feature.domain.model.Article
import com.example.newsappcompose.news_home_feature.domain.model.NewsResponse
import com.example.newsappcompose.news_home_feature.domain.repository.NewsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

val expectedArticle =  listOf( Article(
    author = "John Doe",
    content = "Lorem ipsum dolor sit amet",
    description = "Lorem ipsum dolor sit amet",
    publishedAt = "2023-10-15T12:00:00Z",
    source = SourceDto("1","CNN"),
    title = "Sample Article",
    url = "https://www.example.com/article/1",
    urlToImage = "https://www.example.com/article/1/image.jpg"
))

val fakeErrorNewsRepository = object : NewsRepository {
    override suspend fun getNews(countryCode: String): Response<NewsResponse> {
        return Response.error(
            500,
            "Error".toResponseBody("application/json".toMediaTypeOrNull())
        )
    }
}
val fakeSuccessNewsRepository=object :NewsRepository{
    override suspend fun getNews(countryCode: String): Response<NewsResponse> {
        val articles = listOf(
            Article(
                author = "John Doe",
                content = "Lorem ipsum dolor sit amet",
                description = "Lorem ipsum dolor sit amet",
                publishedAt = "2023-10-15T12:00:00Z",
                source = SourceDto("1","CNN"),
                title = "Sample Article",
                url = "https://www.example.com/article/1",
                urlToImage = "https://www.example.com/article/1/image.jpg"
            )
        )

        val fakeNewsResponse = NewsResponse(articles, "ok", articles.size)
        return Response.success(fakeNewsResponse)

    }
}