package com.svdroid.skoovetest.ui.songs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svdroid.skoovetest.data.repository.SongsRepository
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsListViewModel @Inject constructor(
    private val repository: SongsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<DataState<List<SongUIModel>>>(DataState.Loading())
    val state = _state.asStateFlow()

    init {
        getSongsList()
    }

    private fun getSongsList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSongs().collect { result -> _state.value = result }
        }
    }

    fun refreshSongsList() {
        if (_state.value !is DataState.Loading) {
            _state.update { DataState.Loading() }

            getSongsList()
        }
    }

    fun setFavorite(songId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("UNUSED_VARIABLE") val result = repository.markSongAsFavorite(songId, !isFavorite)
            //todo add result handler
        }
    }
}