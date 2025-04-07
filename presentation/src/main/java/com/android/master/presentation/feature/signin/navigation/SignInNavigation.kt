package com.android.master.presentation.feature.signin.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.signin.SignInRoute

fun NavGraphBuilder.signInGraph(
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
    onShowSnackbar: (String, SnackbarDuration) -> Unit
) {
    composable(route = SignInRoute.ROUTE) {
        SignInRoute(
            navigateToOnboarding = navigateToOnboarding,
            navigateToHome = navigateToHome,
            onShowSnackbar = onShowSnackbar
        )
    }
}

object SignInRoute {
    const val ROUTE = "signIn"
}