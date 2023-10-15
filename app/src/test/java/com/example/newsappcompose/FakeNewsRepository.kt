package com.example.newsappcompose

import com.example.newsappcompose.data.remote.dto.SourceDto
import com.example.newsappcompose.domain.model.Article
import com.example.newsappcompose.domain.model.NewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import retrofit2.Response

class FakeNewsRepository:NewsRepository {
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