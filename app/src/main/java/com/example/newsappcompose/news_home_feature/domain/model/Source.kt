package com.example.newsappcompose.news_home_feature.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(  val id: String?,
                    val name: String?):Parcelable
