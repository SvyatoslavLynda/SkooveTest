package com.svdroid.skoovetest.ui.songs

import com.nhaarman.mockitokotlin2.*
import com.svdroid.skoovetest.data.repository.SongsRepository
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SongsListViewModelTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var viewModel: SongsListViewModel
    private lateinit var repository: SongsRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = SongsListViewModel(repository)
    }

    @Test
    fun testGetSongsListSuccess() {
        val songsList = listOf(
            SongUIModel(
                1L,
                "song1",
                "artist1",
                "coverUrl1",
                2000,
                false,
                0,
            ),
            SongUIModel(
                2L,
                "song2",
                "artist2",
                "coverUrl2",
                2001,
                false,
                0,
            ),
            SongUIModel(
                3L,
                "song3",
                "artist3",
                "coverUrl3",
                2002,
                false,
                0,
            ),
        )

        testScope.launch {

            val songsFlow = flowOf(DataState.Data(songsList))

            whenever(repository.getAllSongs()).thenReturn(songsFlow)

            viewModel.state.collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        // ignore
                    }
                    is DataState.Data<List<SongUIModel>> -> {
                        Assert.assertEquals(songsList, result.data)
                    }
                    is DataState.Error -> {
                        Assert.fail("Unexpected error state")
                    }
                }
            }
        }
    }

    @Test
    fun testGetSongsListError() {
        testScope.launch {
            val errorFlow = flowOf<DataState<List<SongUIModel>>>(DataState.Error("Error getting songs list"))

            whenever(repository.getAllSongs()).thenReturn(errorFlow)

            viewModel.state.collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        // ignore
                    }
                    is DataState.Data<List<SongUIModel>> -> {
                        Assert.fail("Unexpected success state")
                    }
                    is DataState.Error -> {
                        Assert.assertEquals("Error getting songs list", result.message)
                    }
                }
            }
        }
    }

    @Test
    fun testRefreshSongsList() {
        val songsList = listOf(
            SongUIModel(
                1L,
                "song1",
                "artist1",
                "coverUrl1",
                2000,
                false,
                0,
            ),
            SongUIModel(
                2L,
                "song2",
                "artist2",
                "coverUrl2",
                2001,
                false,
                0,
            ),
            SongUIModel(
                3L,
                "song3",
                "artist3",
                "coverUrl3",
                2002,
                false,
                0,
            ),
        )

        testScope.launch {
            val songsFlow = flowOf(DataState.Data(songsList))

            whenever(repository.getAllSongs()).thenReturn(songsFlow)

            viewModel.state.collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        // ignore
                    }
                    is DataState.Data<List<SongUIModel>> -> {
                        Assert.assertEquals(songsList, result.data)
                    }
                    is DataState.Error -> {
                        Assert.assertEquals("Error getting songs list", result.message)
                    }
                }
            }
        }
    }
}