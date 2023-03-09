package com.svdroid.skoovetest.ui.songs.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svdroid.skoovetest.data.repository.SongsRepository
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.utils.extension.DataState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SongDetailsViewModel @AssistedInject constructor(
    @Assisted
    private val songId: Long,
    private val repository: SongsRepository
) : ViewModel() {

    init {
        getSong()
    }

    private val _state = MutableStateFlow<DataState<SongUIModel>>(DataState.Loading())
    val state = _state.asStateFlow()

    private fun getSong() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSong(songId).collect { result -> _state.value = result }
        }
    }

    fun setFavorite(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("UNUSED_VARIABLE") val result = repository.markSongAsFavorite(songId, !isFavorite)
            //todo add result handler
        }
    }

    fun setRating(rating: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            @Suppress("UNUSED_VARIABLE") val result = repository.setSongRating(songId, rating = rating)
            //todo add result handler
        }
    }
}

