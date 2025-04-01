package com.android.master.presentation.feature.navigator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.android.master.presentation.feature.diary.navigation.diaryNavGraph
import com.android.master.presentation.feature.home.navigation.homeNavGraph
import com.android.master.presentation.feature.mypage.navigation.myPageNavGraph
import com.android.master.presentation.feature.navigator.MainNavigator
import com.android.master.presentation.ui.theme.RPAppTheme

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RPAppTheme.colors.white)
    ) {
        NavHost(
            navController = navigator.navHostController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(padding = padding)

            diaryNavGraph(padding = padding)

            myPageNavGraph(padding = padding)
        }
    }
}