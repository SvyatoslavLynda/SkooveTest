package com.svdroid.skoovetest.data.repository.source.db

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.skoovetest.data.db.SongDetailsDao
import com.svdroid.skoovetest.data.db.SongDetailsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SongsDbDataSourceImplTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var dao: SongDetailsDao
    private lateinit var dataSource: SongsDbDataSource

    @Before
    fun setup() {
        dao = mock()
        dataSource = SongsDbDataSourceImpl(dao)
    }

    @Test
    fun testCreateSongs() {
        val song1 = SongDetailsEntity(1, "Song 1", "Artist 1", "Album 1", 2022, false, 0)
        val song2 = SongDetailsEntity(2, "Song 2", "Artist 2", "Album 2", 2023, true, 0)
        val expected = listOf(song1, song2)

        testScope.launch {
            dataSource.createSongs(expected)

            whenever(dao.getSongs()).thenReturn(expected)
            val songs = dao.getSongs()

            assertEquals(expected, songs)
        }
    }

    @Test
    fun testGetSongs() {
        val song1 = SongDetailsEntity(1, "Song 1", "Artist 1", "Album 1", 2022, false, 0)
        val song2 = SongDetailsEntity(2, "Song 2", "Artist 2", "Album 2", 2023, true, 0)
        val expected = listOf(song1, song2)

        testScope.launch {
            dao.createSongDetails(listOf(song1, song2))

            whenever(dao.getSongs()).thenReturn(expected)
            val songs = dataSource.getSongsFlow().first()

            assertEquals(listOf(song1, song2), songs)
        }
    }
}