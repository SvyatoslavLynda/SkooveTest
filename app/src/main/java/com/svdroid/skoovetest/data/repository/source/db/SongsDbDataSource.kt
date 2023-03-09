package com.svdroid.skoovetest.data.repository.source.db

import com.svdroid.skoovetest.data.db.SongDetailsEntity
import kotlinx.coroutines.flow.Flow

interface SongsDbDataSource {

    suspend fun createSongs(songs: List<SongDetailsEntity>)

    fun getSongsFlow(): Flow<List<SongDetailsEntity>>

    fun getSongs(): List<SongDetailsEntity>

    fun getSong(id: Long): Flow<SongDetailsEntity?>

    suspend fun getCurrentFavorite(): SongDetailsEntity?

    suspend fun getSongEntity(id: Long): SongDetailsEntity?

    suspend fun updateSongsData(vararg songs: SongDetailsEntity)
}