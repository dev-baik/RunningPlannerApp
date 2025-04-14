package com.android.master.presentation.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.home.HomeRoute
import com.android.master.presentation.model.MainNavigationBarRoute

fun NavHostController.navigationHome(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.Home::class.simpleName.orEmpty(),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onVideoButtonClicked: () -> Unit
) {
    composable(route = MainNavigationBarRoute.Home::class.simpleName.orEmpty()) {
        HomeRoute(
            padding = padding,
            onVideoButtonClicked = onVideoButtonClicked
        )
    }
}