package com.android.master.presentation.feature.video.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.video.VideoRoute

fun NavController.navigationVideo() {
    navigate(route = VideoRoute.ROUTE)
}

fun NavGraphBuilder.videoNavGraph(
    padding: PaddingValues,
    onShowSnackbar: (String, SnackbarDuration) -> Unit
) {
    composable(route = VideoRoute.ROUTE) {
        VideoRoute(
            padding = padding,
            onShowSnackbar = onShowSnackbar
        )
    }
}

object VideoRoute {
    const val ROUTE = "video"
}