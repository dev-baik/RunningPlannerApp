package com.android.master.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.master.presentation.ui.NavigationRouteName.MAIN_DIARY
import com.android.master.presentation.ui.NavigationRouteName.MAIN_HOME
import com.android.master.presentation.ui.NavigationRouteName.MAIN_MY_PAGE

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector, val title: String) : NavigationItem(route) {
        object Home : MainNav(MAIN_HOME, Icons.Default.Home, NavigationTitle.MAIN_HOME)
        object Diary : MainNav(MAIN_DIARY, Icons.Default.DateRange, NavigationTitle.MAIN_DIARY)
        object MyPage : MainNav(MAIN_MY_PAGE, Icons.Default.AccountCircle, NavigationTitle.MAIN_MY_PAGE)

        companion object {
            fun isMainRoute(route: String?) : Boolean {
                return when (route) {
                    MAIN_HOME, MAIN_DIARY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }
}

object NavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_DIARY = "main_diary"
    const val MAIN_MY_PAGE = "main_my_page"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_DIARY = "다이어리"
    const val MAIN_MY_PAGE = "마이페이지"
}