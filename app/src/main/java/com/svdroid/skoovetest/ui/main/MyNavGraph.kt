package com.svdroid.skoovetest.ui.main

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.svdroid.skoovetest.data.ui.SongUIModel
import com.svdroid.skoovetest.di.songDetailViewModel
import com.svdroid.skoovetest.ui.songs.SongsListScreen
import com.svdroid.skoovetest.ui.songs.details.SongDetailsScreen
import kotlinx.coroutines.InternalCoroutinesApi

object Destinations {
    const val AUDIO_LIST_ROUTE = "audio_list"
    const val AUDIO_DETAIL_ROUTE = "audio_detail"

    object Arguments {
        const val AUDIO = "audio"
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@InternalCoroutinesApi
@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.AUDIO_LIST_ROUTE
) {

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Destinations.AUDIO_LIST_ROUTE) {
            SongsListScreen(actions = actions)
        }
        composable(
            Destinations.AUDIO_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(Destinations.Arguments.AUDIO) {
                    nullable = true
                    type = NavType.ParcelableType(SongUIModel::class.java)
                })
        ) {
            val sound = navController.previousBackStackEntry?.savedStateHandle?.get<SongUIModel>(Destinations.Arguments.AUDIO)
                    ?: return@composable //todo: the state of UI if the songId wasn't provided

            SongDetailsScreen(viewModel = songDetailViewModel(sound.id), navigator = navController)
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {

    val navigateToAudioDetail: (audio: SongUIModel) -> Unit = { audio ->
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            set(Destinations.Arguments.AUDIO, audio)
        }
        navController.navigate(Destinations.AUDIO_DETAIL_ROUTE)
    }
}

