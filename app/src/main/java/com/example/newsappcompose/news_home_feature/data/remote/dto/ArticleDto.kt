package com.example.newsappcompose.news_home_feature.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceDto?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Parcelable
