package com.svdroid.skoovetest.data.repository.source.network

import com.svdroid.skoovetest.data.api.SongsApiService
import com.svdroid.skoovetest.data.api.SongsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsNetworkDataSourceImpl @Inject constructor(
    private val apiService: SongsApiService,
) : SongsNetworkDataSource {

    override suspend fun getSongsList(): SongsResponse {
        return withContext(Dispatchers.IO) {
            apiService.getSongsList()
        }
    }
}