package com.android.master.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.android.master.presentation.R
import com.android.master.presentation.model.MainNavigationBarRoute
import com.android.master.presentation.model.Route

enum class MainNavigationBarItemType(
    @DrawableRes val iconRes: Int,
    @StringRes val label: Int,
    val route: MainNavigationBarRoute
) {
    HOME(
        iconRes = R.drawable.ic_nav_home_selected,
        label = R.string.main_navigation_bar_item_home,
        route = MainNavigationBarRoute.Home
    ),
    DIARY(
        iconRes = R.drawable.ic_nav_diary_selected,
        label = R.string.main_navigation_bar_item_my_page,
        route = MainNavigationBarRoute.Diary
    ),
    MY_PAGE(
        iconRes = R.drawable.ic_nav_my_page_selected,
        label = R.string.main_navigation_bar_item_my_page,
        route = MainNavigationBarRoute.MyPage
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainNavigationBarRoute) -> Boolean): MainNavigationBarItemType? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}