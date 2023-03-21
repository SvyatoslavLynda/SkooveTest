package com.svdroid.skoovetest.data.repository

import com.svdroid.skoovetest.data.repository.source.db.SongsDbDataSource
import com.svdroid.skoovetest.data.repository.source.network.SongsNetworkDataSource
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.data.ui.mapper.toDbEntity
import com.svdroid.skoovetest.data.ui.mapper.toUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.sql.SQLException
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val dbDataSource: SongsDbDataSource,
    private val networkDataSource: SongsNetworkDataSource,
) : SongsRepository {

    override fun getAllSongs(): Flow<DataState<List<SongUIModel>>> = flow {
        try {
            emit(DataState.Loading())
            val flowSongs = dbDataSource.getSongsFlow()
            var localSongItems: List<SongUIModel> = flowSongs.first().map { it.toUIModel() }

            val response = networkDataSource.getSongsList()
            val entities = response.data.map { remoteSoundItem ->
                val localItem = localSongItems.firstOrNull { localSongItem ->
                    remoteSoundItem.title == localSongItem.title // todo: it's better to add id for songs records on backend
                }

                remoteSoundItem.toDbEntity(isFavorite = localItem?.isFavorite, rating = localItem?.rating)
            }

            dbDataSource.createSongs(entities)

            val remoteSongItems = entities.map { it.toUIModel() }
            emit(DataState.Data(remoteSongItems))

            flowSongs.collect { dataFlow ->
                localSongItems = dataFlow.map { it.toUIModel() }
                emit(DataState.Data(localSongItems))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Something went wrong!"))
        }
    }

    override fun getSong(id: Long): Flow<DataState<SongUIModel>> = flow {
        try {
            emit(DataState.Loading())

            dbDataSource.getSong(id).collect { dataFlow ->
                val song = dataFlow?.toUIModel()

                if (song == null) {
                    emit(DataState.Error("Something went wrong!"))
                } else {
                    emit(DataState.Data(song))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Something went wrong!"))
        }
    }

    override suspend fun markSongAsFavorite(songId: Long, isFavorite: Boolean): DataState<Unit> {
        val entity = dbDataSource.getSong(songId).first()

        return try {
            if (entity != null) {
                if (entity.isFavorite == true) {
                    dbDataSource.updateSongsData(entity.copy(isFavorite = false))
                } else {
                    val existingFavoriteEntity = dbDataSource.getCurrentFavorite()

                    if (existingFavoriteEntity == null) {
                        dbDataSource.updateSongsData(entity.copy(isFavorite = true))
                    } else {
                        dbDataSource.updateSongsData(
                            existingFavoriteEntity.copy(isFavorite = !isFavorite),
                            entity.copy(isFavorite = true),
                        )
                    }
                }

                DataState.Data(Unit)
            } else {
                DataState.Error("Song not found")
            }
        } catch (e: SQLException) {
            DataState.Error("Error occurred updating song ${e.message}")
        }
    }

    override suspend fun setSongRating(songId: Long, rating: Int): DataState<Unit> {
        val entity = dbDataSource.getSong(songId).first()

        return try {
            if (entity != null) {
                dbDataSource.updateSongsData(entity.copy(rating = rating))
                DataState.Data(Unit)
            } else {
                DataState.Error("Song not found")
            }
        } catch (e: SQLException) {
            DataState.Error("Error occurred updating song ${e.message}")
        }
    }
}