package com.svdroid.skoovetest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongDetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getSongDetailsDao(): SongDetailsDao
}

