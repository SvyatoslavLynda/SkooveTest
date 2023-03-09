package com.svdroid.skoovetest.data.repository.source.network

import com.svdroid.skoovetest.data.api.SongsResponse

interface SongsNetworkDataSource {

    suspend fun getSongsList(): SongsResponse
}