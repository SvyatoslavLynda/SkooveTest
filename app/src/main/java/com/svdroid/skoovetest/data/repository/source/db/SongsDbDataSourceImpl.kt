package com.svdroid.skoovetest.data.repository.source.db

import com.svdroid.skoovetest.data.db.SongDetailsDao
import com.svdroid.skoovetest.data.db.SongDetailsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongsDbDataSourceImpl @Inject constructor(
    private val songDataDao: SongDetailsDao,
) : SongsDbDataSource {

    override suspend fun createSongs(songs: List<SongDetailsEntity>) {
        withContext(Dispatchers.IO) {
            songDataDao.deleteSongDetails()
            songDataDao.createSongDetails(songs)
        }
    }

    override fun getSongsFlow(): Flow<List<SongDetailsEntity>> {
        return songDataDao.getSongsFlow()
    }

    override fun getSong(id: Long): Flow<SongDetailsEntity?> {
        return songDataDao.getSong(id)
    }

    override suspend fun getCurrentFavorite(): SongDetailsEntity? {
        return withContext(Dispatchers.IO) { songDataDao.getFavoriteSongDetails() }
    }

    override suspend fun updateSongsData(vararg songs: SongDetailsEntity) {
        return withContext(Dispatchers.IO) { songDataDao.updateSongsDetails(songs = songs) }
    }

}