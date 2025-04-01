package com.android.master.presentation.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.home.HomeRoute
import com.android.master.presentation.model.MainNavigationBarRoute

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues
) {
    composable(route = MainNavigationBarRoute.Home::class.simpleName.orEmpty()) {
        HomeRoute(
            padding = padding
        )
    }
}

object HomeRoute {
    const val ROUTE = "home"
}