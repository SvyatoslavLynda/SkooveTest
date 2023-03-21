@file:OptIn(ExperimentalCoroutinesApi::class)

package com.svdroid.skoovetest.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.skoovetest.data.api.SongsResponse
import com.svdroid.skoovetest.data.db.SongDetailsEntity
import com.svdroid.skoovetest.data.repository.source.db.SongsDbDataSource
import com.svdroid.skoovetest.data.repository.source.network.SongsNetworkDataSource
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SongsRepositoryImplTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var dbDataSource: SongsDbDataSource
    private lateinit var networkDataSource: SongsNetworkDataSource
    private lateinit var songsRepository: SongsRepositoryImpl

    @Before
    fun setUp() {
        dbDataSource = mock()
        networkDataSource = mock()
        songsRepository = SongsRepositoryImpl(dbDataSource, networkDataSource)
    }

    @Test
    fun getAllSongsShouldEmitDataStateLoading() {
        testScope.launch {
            val expected = emptyList<SongDetailsEntity>()

            whenever(dbDataSource.getSongsFlow()).thenReturn(flow {
                emit(expected)
            })

            whenever(networkDataSource.getSongsList()).thenReturn(SongsResponse(data = emptyList()))

            val result = songsRepository.getAllSongs().first()

            assertEquals(DataState.Loading<List<SongUIModel>>(), result)
        }
    }

    @Test
    fun getSongShouldEmitDataStateLoading() {
        testScope.launch {
            whenever(dbDataSource.getSong(anyLong())).thenReturn(flow {
                emit(mock())
            })

            val result = songsRepository.getSong(1).first()

            assertEquals(DataState.Loading<SongUIModel>(), result)
        }
    }

    @Test
    fun markSongAsFavoriteShouldReturnDataStateData() {
        testScope.launch {
            whenever(dbDataSource.getSong(anyLong())).thenReturn(flow {
                emit(mock())
            })

            whenever(dbDataSource.updateSongsData(mock())).thenReturn(Unit)

            val result = songsRepository.markSongAsFavorite(1, true)

            assertEquals(DataState.Data(Unit), result)
        }
    }

    @Test
    fun setSongRatingShouldReturnDataStateData() {
        testScope.launch {
            whenever(dbDataSource.getSong(anyLong())).thenReturn(flow {
                emit(mock())
            })

            whenever(dbDataSource.updateSongsData(mock())).thenReturn(Unit)

            val result = songsRepository.setSongRating(1, 5)

            assertEquals(DataState.Data(Unit), result)
        }
    }
}