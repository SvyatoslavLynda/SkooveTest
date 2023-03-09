package com.svdroid.skoovetest.ui.songs.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.svdroid.skoovetest.R
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.ui.MediaPlayerController
import com.svdroid.skoovetest.ui.component.AudioDetailItem
import com.svdroid.skoovetest.utils.extension.DataState

@Composable
fun SongDetailsScreen(
    viewModel: SongDetailsViewModel,
    navigator: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    when {
        (state as? DataState.Data<SongUIModel>)?.data != null -> SongDataState(
            (state as? DataState.Data<SongUIModel>)!!.data!!,
            navigator,
            viewModel,
        )
        state is DataState.Loading -> SongLoadingState(navigator)
        state is DataState.Error -> SongErrorState(
            error = (state as DataState.Error<SongUIModel>).message,
            navigator = navigator,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongDataState(
    song: SongUIModel,
    navigator: NavHostController,
    viewModel: SongDetailsViewModel,
    mediaPlayerController: MediaPlayerController = hiltViewModel(),
) {
    fun navigationBackHandler() {
        mediaPlayerController.releaseMediaPlayer()
        navigator.popBackStack()
    }

    BackHandler { navigationBackHandler() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = song.title) },
                navigationIcon = {
                    IconButton(
                        onClick = { navigationBackHandler() },
                        content = { Icon(Icons.Filled.Close, contentDescription = "CloseButton") }
                    )
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AudioDetailItem(
                    audio = song,
                    mediaPlayer = mediaPlayerController,
                    onStarClicked = { viewModel.setRating(it) },
                    onFavoriteClicked = { viewModel.setFavorite(song.isFavorite) },
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongLoadingState(navigator: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.audio_list_screen_app_top_bar_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
                        content = { Icon(Icons.Filled.Close, contentDescription = "CloseButton") }
                    )
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    content = {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(id = R.string.message_loading))
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongErrorState(error: String, navigator: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.audio_list_screen_app_top_bar_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() },
                        content = { Icon(Icons.Filled.Close, contentDescription = "CloseButton") }
                    )
                }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    content = {
                        Text(
                            text = stringResource(id = R.string.formatted_message_error, error),
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    )
}