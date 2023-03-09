package com.svdroid.skoovetest.di

import com.svdroid.skoovetest.data.repository.source.db.SongsDbDataSource
import com.svdroid.skoovetest.data.repository.source.db.SongsDbDataSourceImpl
import com.svdroid.skoovetest.data.repository.source.network.SongsNetworkDataSource
import com.svdroid.skoovetest.data.repository.source.network.SongsNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {

    @Binds
    abstract fun bindsNetworkDataSource(networkDataSource: SongsNetworkDataSourceImpl): SongsNetworkDataSource

    @Binds
    abstract fun bindsDbDataSource(dbDataSource: SongsDbDataSourceImpl): SongsDbDataSource
}