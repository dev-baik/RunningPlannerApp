package com.android.master.presentation.feature.navigator.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.android.master.presentation.feature.diary.navigation.diaryNavGraph
import com.android.master.presentation.feature.home.navigation.homeNavGraph
import com.android.master.presentation.feature.mypage.navigation.myPageNavGraph
import com.android.master.presentation.feature.navigator.MainNavigator
import com.android.master.presentation.feature.onboarding.navigation.onboardingNavGraph
import com.android.master.presentation.feature.signin.navigation.signInGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
    onShowSnackbar: (String, SnackbarDuration) -> Unit
) {
    NavHost(
        navController = navigator.navHostController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        homeNavGraph(padding = padding)

        diaryNavGraph(padding = padding)

        myPageNavGraph(padding = padding)

        signInGraph(
            navigateToOnboarding = navigator::navigateToOnboarding,
            navigateToHome = navigator::navigateToHome,
            onShowSnackbar = onShowSnackbar
        )

        onboardingNavGraph()
    }
}