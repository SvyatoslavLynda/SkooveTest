package com.svdroid.skoovetest.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createSongDetails(songs: List<SongDetailsEntity>)

    @Query("SELECT * FROM SongDetailsEntity")
    fun getSongsFlow(): Flow<List<SongDetailsEntity>>

    @Query("SELECT * FROM SongDetailsEntity")
    fun getSongs(): List<SongDetailsEntity>

    @Query("SELECT * FROM SongDetailsEntity WHERE id=:id")
    fun getSong(id: Long): Flow<SongDetailsEntity?>

    @Query("SELECT * FROM SongDetailsEntity WHERE is_favorite=1")
    suspend fun getFavoriteSongDetails(): SongDetailsEntity?

    @Query("SELECT * FROM SongDetailsEntity WHERE id=:id")
    suspend fun getSongDetails(id: Long): SongDetailsEntity?

    @Update
    fun updateSongsDetails(vararg songs: SongDetailsEntity)

    @Query("DELETE FROM SongDetailsEntity")
    fun deleteSongDetails()
}