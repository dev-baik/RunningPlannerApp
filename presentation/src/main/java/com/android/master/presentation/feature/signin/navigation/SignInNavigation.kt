package com.android.master.presentation.feature.signin.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.signin.SignInRoute

fun NavGraphBuilder.signInGraph(
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = SignInRoute.ROUTE) {
        SignInRoute(
            navigateToOnboarding = navigateToOnboarding,
            navigateToHome = navigateToHome
        )
    }
}

object SignInRoute {
    const val ROUTE = "signIn"
}