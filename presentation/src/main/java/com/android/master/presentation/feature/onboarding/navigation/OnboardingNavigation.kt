package com.android.master.presentation.feature.onboarding.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
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

fun NavGraphBuilder.onboardingNavGraph(
    padding: PaddingValues,
    navigateToHome: () -> Unit,
    onShowSnackbar: (String, SnackbarDuration) -> Unit,
) {
    composable(route = OnboardingRoute.ROUTE) {
        OnboardingRoute(
            padding = padding,
            navigateToHome = navigateToHome,
            onShowSnackbar = onShowSnackbar
        )
    }
}

object OnboardingRoute {
    const val ROUTE = "onboarding"
}