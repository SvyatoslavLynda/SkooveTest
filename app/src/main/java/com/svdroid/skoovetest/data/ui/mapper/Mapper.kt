package com.svdroid.skoovetest.data.ui.mapper

import com.svdroid.skoovetest.data.api.SongsDetailsResponse
import com.svdroid.skoovetest.data.db.SongDetailsEntity
import com.svdroid.skoovetest.data.ui.SongUIModel

fun SongDetailsEntity.toUIModel(): SongUIModel = SongUIModel(
    id = id,
    title = title,
    audioUrl = audioUrl,
    coverUrl = coverUrl,
    duration = duration,
    isFavorite = isFavorite == true,
    rating = rating ?: 0,
)

fun SongsDetailsResponse.toDbEntity(
    isFavorite: Boolean? = null,
    rating: Int? = null,
): SongDetailsEntity = SongDetailsEntity(
    id = 0L,
    title = title,
    audioUrl = audio,
    coverUrl = cover,
    duration = duration,
    isFavorite = isFavorite,
    rating = rating,
)