package com.svdroid.skoovetest.di

import com.svdroid.skoovetest.data.repository.SongsRepository
import com.svdroid.skoovetest.data.repository.SongsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsSongRepository(repository: SongsRepositoryImpl): SongsRepository
}