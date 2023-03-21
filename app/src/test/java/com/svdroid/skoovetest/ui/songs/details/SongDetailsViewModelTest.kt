package com.svdroid.skoovetest.ui.songs.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svdroid.skoovetest.data.repository.SongsRepository
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.api.support.membermodification.MemberMatcher.method

@ExperimentalCoroutinesApi
class SongDetailsViewModelTest {
    private val job = Job()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(job + testDispatcher)

    private lateinit var repository: SongsRepository
    private val songId = 1L

    private lateinit var viewModel: SongDetailsViewModel

    @Before
    fun setUp() {
        repository = mock()
        viewModel = SongDetailsViewModel(songId, repository)
    }

    @Test
    fun getSongShouldUpdateStateWithSongData() {
        val song = SongUIModel(songId, "Song Title", "Artist Name", "Album Name", 2022, true, 4)

        testScope.launch {
            whenever(repository.getSong(songId)).thenReturn(flowOf(DataState.Data(song)))

            val spy = spy(viewModel)

            doNothing().`when`(spy, method(SongDetailsViewModel::class.java, "getSong"))

            verifyPrivate(spy).invoke("getSong")

            val expectedState = DataState.Data(song)
            val actualState = viewModel.state.value

            assertEquals(expectedState, actualState)
        }
    }

    @Test
    fun markSongAsFavoriteShouldBeCalledWithCorrectParametersWhenSetFavoriteIsCalled() {
        val songId = 1L
        val isFavorite = true

        viewModel.setFavorite(isFavorite)

        testScope.launch {
            verify(repository).markSongAsFavorite(songId, false)
        }
    }

    @Test
    fun setRatingShouldCallRepositorySetSongRating() {
        val rating = 4

        viewModel.setRating(rating)

        testScope.launch {
            verify(repository).setSongRating(songId, rating)
        }
    }
}