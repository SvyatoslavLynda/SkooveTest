package com.svdroid.skoovetest.ui.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.svdroid.skoovetest.R
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.ui.component.AudioListItem
import com.svdroid.skoovetest.ui.main.MainActions
import com.svdroid.skoovetest.utils.extension.DataState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SongsListScreen(viewModel: SongsListViewModel = hiltViewModel(), actions: MainActions) {
    val state by viewModel.state.collectAsState()
    val isRefreshing = state is DataState.Loading
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = { viewModel.refreshSongsList() })

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.audio_list_screen_app_top_bar_title)) }) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                when {
                    state is DataState.Data && (state as DataState.Data<*>).data != null -> DataList(
                        songs = ((state as DataState.Data<*>).data!! as List<*>).map { it as SongUIModel },
                        actions = actions,
                        viewModel,
                    )
                    state is DataState.Error -> ErrorState(
                        modifier = Modifier.align(Alignment.Center),
                        (state as DataState.Error<*>).message,
                    )
                    state is DataState.Loading -> LoadingState(modifier = Modifier.align(Alignment.Center))
                }

                PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            }
        }
    )
}

@Composable
fun DataList(songs: List<SongUIModel>, actions: MainActions, viewModel: SongsListViewModel) {
    LazyColumn {
        items(
            count = songs.size,
            itemContent = { index ->
                val item = songs[index]

                AudioListItem(
                    audio = item,
                    onFavoriteClicked = { viewModel.setFavorite(item.id, item.isFavorite) },
                    onItemClicked = { actions.navigateToAudioDetail.invoke(item) },
                )
            }
        )
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Text(text = stringResource(id = R.string.message_loading), modifier = modifier)
}

@Composable
fun ErrorState(modifier: Modifier = Modifier, text: String = stringResource(id = R.string.message_error)) {
    Text(
        text = stringResource(R.string.formatted_message_error, text),
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center
    )
}