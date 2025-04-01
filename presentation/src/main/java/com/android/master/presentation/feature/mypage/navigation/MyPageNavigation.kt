package com.android.master.presentation.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.mypage.MyPageRoute
import com.android.master.presentation.model.MainNavigationBarRoute

fun NavHostController.navigationMyPage(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.MyPage::class.simpleName.orEmpty(),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues
) {
    composable(route = MainNavigationBarRoute.MyPage::class.simpleName.orEmpty()) {
        MyPageRoute(
            padding = padding
        )
    }
}