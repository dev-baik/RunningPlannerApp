package com.android.master.presentation.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.onboarding.OnboardingRoute

fun NavController.navigationOnboarding(navOptions: NavOptions) {
    navigate(
        route = OnboardingRoute.ROUTE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.onboardingNavGraph() {
    composable(route = OnboardingRoute.ROUTE) {
        OnboardingRoute()
    }
}

object OnboardingRoute {
    const val ROUTE = "onboarding"
}