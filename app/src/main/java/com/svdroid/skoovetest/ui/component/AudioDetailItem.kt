package com.svdroid.skoovetest.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.svdroid.skoovetest.R
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.ui.MediaPlayerController
import com.svdroid.skoovetest.ui.MediaPlayerState
import com.svdroid.skoovetest.utils.extension.timeStampToDuration

/**
 * Audio item for the detail view
 */
@Composable
fun AudioDetailItem(
    audio: SongUIModel,
    mediaPlayer: MediaPlayerController = hiltViewModel(),
    onStarClicked: (rating: Int) -> Unit,
    onFavoriteClicked: (favorite: Boolean) -> Unit
) {
    var audioPlayerState by remember { mutableStateOf<MediaPlayerState>(MediaPlayerState.None) }

    audioPlayerState = mediaPlayer.mediaPlayerState.collectAsState().value

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    mediaPlayer.audioSelected(audio.audioUrl)
                },
            contentAlignment = Alignment.Center
        ) {

            // Audio Cover
            CoilImage(
                imageModel = { audio.coverUrl },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )

            // Media Player Controller Icons
            when (audioPlayerState) {
                MediaPlayerState.Initialization -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(color = Color.Black.copy(alpha = 0.95f))
                            .padding(16.dp)
                    )
                }
                MediaPlayerState.Started, MediaPlayerState.Paused, MediaPlayerState.Finished, MediaPlayerState.Initialized -> {
                    Image(
                        painter = painterResource(id = if (audioPlayerState == MediaPlayerState.Started) R.drawable.ic_pause else R.drawable.ic_play),
                        contentDescription = stringResource(id = R.string.contentDescription_audio_is_favorite),
                        modifier = Modifier.size(120.dp)
                    )
                }
                else -> {}
            }

            // audio favorite status element
            FavoriteElement(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(color = Color.White.copy(alpha = 0.5f))
                .padding(16.dp),
                favoriteState = audio.isFavorite,
                onClick = {
                    onFavoriteClicked((audio.isFavorite))
                })
        }

        Spacer(modifier = Modifier.size(32.dp))

        // Time
        PlayerTimer(duration = audio.duration, mediaPlayer = mediaPlayer)

        // Audio Slider
        AppSlider(duration = audio.duration, mediaPlayer = mediaPlayer)

        Spacer(modifier = Modifier.size(32.dp))

        // Rating
        RatingStars(modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.75f)),
            rating = audio.rating,
            starSize = 64,
            onStarClicked = { rating ->
                onStarClicked(rating)
            })
    }
}

@Composable
fun PlayerTimer(duration: Long, mediaPlayer: MediaPlayerController) {
    var playingTime by remember { mutableStateOf(mediaPlayer.playingTimeMs.value) }
    playingTime = mediaPlayer.playingTimeMs.collectAsState().value

    Text(
        modifier = Modifier.wrapContentWidth(),
        textAlign = TextAlign.Center,
        text = "${playingTime.toLong().timeStampToDuration()} / ${duration.timeStampToDuration()}",
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun AppSlider(duration: Long, mediaPlayer: MediaPlayerController) {
    var playingTime by remember { mutableStateOf(mediaPlayer.playingTimeMs.value) }
    playingTime = mediaPlayer.playingTimeMs.collectAsState().value

    // getting current interaction with slider - are we pressing or dragging?
    val interactionSource = remember { MutableInteractionSource() }


    Slider(
        value = playingTime.toFloat(),
        onValueChange = {
            playingTime = it.toInt()
            mediaPlayer.seekMediaPlayer(playingTime)
        },
        onValueChangeFinished = {
            mediaPlayer.seekMediaPlayer(playingTime)
        },
        interactionSource = interactionSource,
        valueRange = 0f..duration.toFloat(),
        steps = duration.toInt() / 100,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.secondary,
            activeTickColor = MaterialTheme.colorScheme.secondary,
            inactiveTickColor = MaterialTheme.colorScheme.onError,
        )
    )
}