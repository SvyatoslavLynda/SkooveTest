package com.svdroid.skoovetest.ui.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun MyApp() {

    MaterialTheme {
        val navController = rememberNavController()

        MyNavGraph(navController = navController)
    }
}
