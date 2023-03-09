package com.svdroid.skoovetest.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Media player controller wrapped as a ViewModel exposing its current state
 */
class MediaPlayerController : ViewModel() {

    // object of media player
    private var mediaPlayer: MediaPlayer? = null

    // sealed class for handling different media player states
    private val _mediaPlayerState = MutableStateFlow<MediaPlayerState>(MediaPlayerState.None)
    val mediaPlayerState = _mediaPlayerState.asStateFlow()

    private val _playingTimeMs = MutableStateFlow(0)
    val playingTimeMs = _playingTimeMs.asStateFlow()

    private val handler = Handler(Looper.getMainLooper())

    private var playingTimeRunnable = object : Runnable {
        override fun run() {
            _playingTimeMs.value = mediaPlayer?.currentPosition ?: 0
            handler.postDelayed(this, 300)
        }
    }

    // Media player attributes
    private val attributes = AudioAttributes
        .Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()

    /**
     * Media player click handler
     */
    fun audioSelected(url: String) {
        when (mediaPlayerState.value) {
            MediaPlayerState.None -> initializeMediaPlayer(url)
            MediaPlayerState.Started -> pauseMediaPlayer()
            MediaPlayerState.Paused, MediaPlayerState.Initialized, MediaPlayerState.Finished -> startMediaPlayer()
            else -> {
            }
        }
    }

    /**
     * Initialize media player with given url
     */
    private fun initializeMediaPlayer(url: String) {
        _mediaPlayerState.update { MediaPlayerState.Initialization }

        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioAttributes(attributes)
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.setOnPreparedListener {
                _mediaPlayerState.update { MediaPlayerState.Initialized }
                startMediaPlayer()
            }
            mediaPlayer?.setOnCompletionListener {
                releaseMediaPlayer()
            }
            mediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Start media player
     */
    private fun startMediaPlayer() {
        try {
            mediaPlayer?.start()
            _mediaPlayerState.update { MediaPlayerState.Started }
            playingTimeRunnable.run()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Pause media player
     */
    private fun pauseMediaPlayer() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            _mediaPlayerState.update { MediaPlayerState.Paused }
            handler.removeCallbacksAndMessages(null)
        }
    }


    /**
     * Release media player
     */
    fun releaseMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _playingTimeMs.update { 0 }
        _mediaPlayerState.update { MediaPlayerState.None }
        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Seek to new position
     */
    fun seekMediaPlayer(newPosition: Int) {
        mediaPlayer?.seekTo(newPosition)
    }
}

/**
 * Media player state
 *
 * @constructor Create empty Media player state
 */
sealed class MediaPlayerState {
    object None : MediaPlayerState()
    object Initialization : MediaPlayerState()
    object Initialized : MediaPlayerState()
    object Started : MediaPlayerState()
    object Paused : MediaPlayerState()
    object Finished : MediaPlayerState()
}