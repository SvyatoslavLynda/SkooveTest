package com.svdroid.skoovetest.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "audio_url")  val audioUrl: String,
    @ColumnInfo(name = "cover_url")  val coverUrl: String,
    @ColumnInfo(name = "duration")  val duration: Long,
    @ColumnInfo(name = "is_favorite")  val isFavorite: Boolean?,
    @ColumnInfo(name = "rating")  val rating: Int?,
)