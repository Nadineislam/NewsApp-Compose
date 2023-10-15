package com.example.newsappcompose.domain.model

import android.os.Parcelable
import com.example.newsappcompose.data.remote.dto.SourceDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(val author: String?,
                   val content: String?,
                   val description: String?,
                   val publishedAt: String?,
                   val source: SourceDto?,
                   val title: String?,
                   val url: String?,
                   val urlToImage: String?):Parcelable
