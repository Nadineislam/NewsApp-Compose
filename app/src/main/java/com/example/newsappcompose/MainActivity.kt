package com.example.newsappcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import coil.compose.rememberImagePainter
import com.example.newsappcompose.model.Article
import com.example.newsappcompose.model.NewsResponse
import com.example.newsappcompose.repository.NewsRepository
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme
import com.example.newsappcompose.utils.Resource
import com.example.newsappcompose.viewmodel.NewsViewModel
import com.example.newsappcompose.viewmodel.NewsViewModelProviderFactory

class MainActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelProviderFactory(NewsRepository())
    }

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

@Composable
fun NewsApp(viewModel: NewsViewModel) {
    val news = remember { mutableStateOf<Resource<NewsResponse>>(Resource.Loading()) }

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
    DisposableEffect(viewModel) {
        val observer = Observer<Resource<NewsResponse>> { resource ->
            news.value = resource
        }
        viewModel.observeNewsLiveData().observeForever(observer)
        onDispose {
            viewModel.observeNewsLiveData().removeObserver(observer)
        }
    }
}

@Composable
fun NewsList(articles: List<Article>?) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(15.dp),
    ) {
        items(articles ?: emptyList()) { article ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clickable {
                    val intent = Intent(context, NewDetails::class.java)
                    intent.putExtra("article", article)
                    context.startActivity(intent)
                }, elevation = 3.dp
            ) {
                NewsArticle(article)
            }
        }
    }
}

@Composable
fun NewsArticle(article: Article) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter(article.urlToImage),
            contentDescription = "News Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
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
