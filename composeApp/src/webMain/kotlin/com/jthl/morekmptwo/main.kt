package com.jthl.morekmptwo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import androidx.navigation.compose.rememberNavController
import kotlin.js.js


@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {

    ComposeViewport {
        MaterialTheme {
            val navController = rememberNavController()
            LaunchedEffect(navController) {
                navController.bindToBrowserNavigation()
            }
            App(navController)
        }

        alert(StorageManager.getUserConfig() + "")
    }

}


