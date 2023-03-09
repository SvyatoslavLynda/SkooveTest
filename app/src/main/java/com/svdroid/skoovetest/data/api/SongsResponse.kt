package com.svdroid.skoovetest.data.api

import com.google.gson.annotations.SerializedName

data class SongsResponse(
    @SerializedName("data") val data: List<SongsDetailsResponse>,
)

data class SongsDetailsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("audio") val audio: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("totalDurationMs") val duration: Long,
)
