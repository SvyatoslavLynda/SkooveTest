package com.svdroid.skoovetest.data.repository

import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import kotlinx.coroutines.flow.Flow

interface SongsRepository {
    fun getAllSongs(): Flow<DataState<List<SongUIModel>>>

    fun getSong(id: Long): Flow<DataState<SongUIModel>>

    suspend fun markSongAsFavorite(songId: Long, isFavorite: Boolean): DataState<Unit>

    suspend fun setSongRating(songId: Long, rating: Int): DataState<Unit>
}