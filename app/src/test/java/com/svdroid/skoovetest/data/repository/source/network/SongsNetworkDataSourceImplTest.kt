package com.svdroid.skoovetest.data.repository.source.network

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.skoovetest.data.api.SongsApiService
import com.svdroid.skoovetest.data.api.SongsDetailsResponse
import com.svdroid.skoovetest.data.api.SongsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class SongsNetworkDataSourceImplTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    @Test
    fun getSongsListShouldReturnSongsResponse() {
        val songsApiService: SongsApiService = mock()
        val songsNetworkDataSource = SongsNetworkDataSourceImpl(songsApiService)
        val expectedSongsResponse = SongsResponse(
            listOf(
                SongsDetailsResponse(
                    title = "title_1",
                    audio = "https://examplee.com/audio1.mp3",
                    cover = "https://examplee.com/image1.mp3",
                    duration = 3600
                ),
                SongsDetailsResponse(
                    title = "title_2",
                    audio = "https://examplee.com/audio1.mp3",
                    cover = "https://examplee.com/image2.mp3",
                    duration = 3601
                ),
            )
        )

        testScope.launch {
            whenever(songsApiService.getSongsList()).thenReturn(expectedSongsResponse)

            val actualSongsResponse = songsNetworkDataSource.getSongsList()

            assertEquals(expectedSongsResponse, actualSongsResponse)
        }
    }
}