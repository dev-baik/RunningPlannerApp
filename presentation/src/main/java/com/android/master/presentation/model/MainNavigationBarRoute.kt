package com.android.master.presentation.model

sealed interface Route

sealed interface MainNavigationBarRoute : Route {
    data object Home : MainNavigationBarRoute
    data object Diary : MainNavigationBarRoute
    data object MyPage : MainNavigationBarRoute
}