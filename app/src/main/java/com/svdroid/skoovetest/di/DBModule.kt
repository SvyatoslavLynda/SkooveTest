package com.svdroid.skoovetest.di

import android.content.Context
import androidx.room.Room
import com.svdroid.skoovetest.data.db.AppDatabase
import com.svdroid.skoovetest.data.db.SongDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, name = "AppDatabase").build()
    }

    @Provides
    @Singleton
    fun provideRateDao(db: AppDatabase): SongDetailsDao {
        return db.getSongDetailsDao()
    }
}