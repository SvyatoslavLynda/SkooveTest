package com.svdroid.skoovetest.data.api

import retrofit2.http.GET

interface SongsApiService {

    @GET("/data/skoove/manifest.json")
    suspend fun getSongsList(): SongsResponse
}