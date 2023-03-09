package com.svdroid.skoovetest.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.svdroid.skoovetest.ui.main.MainActivity
import com.svdroid.skoovetest.ui.songs.details.SongDetailsViewModel
import dagger.assisted.AssistedFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.InternalCoroutinesApi

@AssistedFactory
interface Factory {
    fun create(songId: Long): SongDetailsViewModel
}

@Suppress("UNCHECKED_CAST")
fun provideFactory(
    assistedFactory: Factory,
    songId: Long
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(songId) as T
    }
}

@OptIn(InternalCoroutinesApi::class)
@Composable
fun songDetailViewModel(songId: Long): SongDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).songDetailViewModelFactory()

    return viewModel(factory = provideFactory(factory, songId))
}