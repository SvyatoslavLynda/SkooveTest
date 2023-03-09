package com.svdroid.skoovetest.data.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SongUIModel(
    val id: Long,
    val title: String,
    val audioUrl: String,
    val coverUrl: String,
    val duration: Long,
    val isFavorite: Boolean,
    val rating: Int,
) : Parcelable