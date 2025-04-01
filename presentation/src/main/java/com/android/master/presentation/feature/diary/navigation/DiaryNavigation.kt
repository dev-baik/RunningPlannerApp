package com.android.master.presentation.feature.diary.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.diary.DiaryRoute
import com.android.master.presentation.model.MainNavigationBarRoute

fun NavHostController.navigationDiary(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.Diary::class.simpleName.orEmpty(),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.diaryNavGraph(
    padding: PaddingValues
) {
    composable(route = MainNavigationBarRoute.Diary::class.simpleName.orEmpty()) {
        DiaryRoute(
            padding = padding
        )
    }
}