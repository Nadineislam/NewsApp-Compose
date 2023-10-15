package com.example.newsappcompose.presentation.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.newsappcompose.presentation.ui.theme.NewsAppComposeTheme
import com.example.newsappcompose.core.utils.Resource
import com.example.newsappcompose.domain.model.Article
import com.example.newsappcompose.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NewsApp(viewModel = viewModel)
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@ExperimentalCoilApi
@Composable
fun NewsApp(viewModel: NewsViewModel) {
    val news = viewModel.news.collectAsState()

    when (val resource = news.value) {
        is Resource.Success -> {
            val articles = resource.data?.articles
            NewsList(articles)
        }
        is Resource.Error -> {
            val message = resource.message ?: "Error fetching news"
            Text(text = message)
        }
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }


}

@ExperimentalCoilApi
@Composable
fun NewsList(articles: List<Article>?) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(15.dp),
    ) {
        items(articles ?: emptyList()) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable {
                        val intent = Intent(context, NewDetails::class.java)
                        intent.putExtra("article", article)
                        context.startActivity(intent)
                    }, elevation = 3.dp, shape = RoundedCornerShape(20.dp)
            ) {
                NewsArticle(article)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun NewsImage(article: Article) {
    Box(
        modifier = Modifier
            .height(140.dp)
            .width(140.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = article.urlToImage)
                .apply(block = fun ImageRequest.Builder.() {
                    transformations(

                        CircleCropTransformation()
                    )
                }).build()
        )
        Image(painter = painter, contentDescription = "news image")
    }
}

@ExperimentalCoilApi
@Composable
fun NewsArticle(article: Article) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        NewsImage(article = article)

        Column(
            modifier = Modifier
        ) {

            Text(
                text = article.title ?: "",
                style = MaterialTheme.typography.h6,
                maxLines = 2
            )

            Text(
                text = article.author ?: "",
                style = MaterialTheme.typography.body1,
                maxLines = 2

            )

            Text(
                text = article.publishedAt ?: "",
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            )
        }
    }

}
