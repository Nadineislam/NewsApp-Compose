package com.example.newsappcompose.news_home_feature.data.remote.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class SourceDto(
    val id: String?,
    val name: String?
):Parcelable