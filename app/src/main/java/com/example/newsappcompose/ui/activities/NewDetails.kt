package com.example.newsappcompose.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.newsappcompose.model.Article
import com.example.newsappcompose.ui.theme.NewsAppComposeTheme


class NewDetails : ComponentActivity() {
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
                    val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra("article", Article::class.java)
                    } else {
                        intent.getParcelableExtra("article")
                    }
                    NewsArticleDetail(article = article as Article)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CoilImageDetail(article: Article) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        val painter = rememberImagePainter(data = article.urlToImage)
        Image(painter = painter, contentDescription = "news image")
    }
}

@ExperimentalCoilApi
@Composable
fun NewsArticleDetail(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CoilImageDetail(article = article)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.author ?: "",
            style = MaterialTheme.typography.subtitle1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.content ?: "",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = article.publishedAt ?: "",
            style = MaterialTheme.typography.caption,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
