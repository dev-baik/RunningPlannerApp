package com.android.master.presentation.feature.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.signin.SignInRoute

fun NavGraphBuilder.signInGraph(
    padding: PaddingValues,
    navigateToHome: () -> Unit
) {
    composable(route = SignInRoute.ROUTE) {
        SignInRoute(
            padding = padding,
            navigateToHome = navigateToHome
        )
    }
}

object SignInRoute {
    const val ROUTE = "signIn"
}