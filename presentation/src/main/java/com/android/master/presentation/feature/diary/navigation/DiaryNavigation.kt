package com.android.master.presentation.feature.diary.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.master.presentation.feature.diary.DiaryRoute
import com.android.master.presentation.model.MainNavigationBarRoute

fun NavGraphBuilder.diaryNavGraph(
    padding: PaddingValues
) {
    composable(route = MainNavigationBarRoute.Diary::class.simpleName.orEmpty()) {
        DiaryRoute(
            padding = padding
        )
    }
}