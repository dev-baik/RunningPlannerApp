package com.android.master.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.android.master.domain.model.TempItem
import com.android.master.presentation.ui.NavigationRouteName.MAIN_DIARY
import com.android.master.presentation.ui.NavigationRouteName.MAIN_HOME
import com.android.master.presentation.ui.NavigationRouteName.MAIN_MY_PAGE
import com.android.master.presentation.utils.GsonUtils

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val icon: ImageVector, val title: String) : NavigationItem(route) {
        object Home : MainNav(MAIN_HOME, Icons.Default.Home, NavigationTitle.MAIN_HOME)
        object Diary : MainNav(MAIN_DIARY, Icons.Default.DateRange, NavigationTitle.MAIN_DIARY)
        object MyPage : MainNav(MAIN_MY_PAGE, Icons.Default.AccountCircle, NavigationTitle.MAIN_MY_PAGE)

        companion object {
            fun isMainRoute(route: String?): Boolean {
                return when (route) {
                    MAIN_HOME, MAIN_DIARY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }
}

object Temp : DestinationArg<TempItem> {
    override val route: String = NavigationRouteName.TEMP
    override val title: String = NavigationTitle.TEMP
    override val argName: String = "tempArg"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun navigateWithArg(item: TempItem): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): TempItem? {
        val tempString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<TempItem>(tempString)
    }
}

interface Destination {
    val route: String
    val title: String
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_DIARY = "main_diary"
    const val MAIN_MY_PAGE = "main_my_page"
    const val TEMP = "temp"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_DIARY = "다이어리"
    const val MAIN_MY_PAGE = "마이페이지"
    const val TEMP = "임시"
}