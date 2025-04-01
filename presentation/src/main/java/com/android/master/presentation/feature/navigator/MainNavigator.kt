package com.android.master.presentation.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.android.master.presentation.feature.diary.navigation.navigationDiary
import com.android.master.presentation.feature.home.navigation.navigationHome
import com.android.master.presentation.feature.mypage.navigation.navigationMyPage
import com.android.master.presentation.feature.signin.navigation.SignInRoute
import com.android.master.presentation.model.MainNavigationBarRoute
import com.android.master.presentation.type.MainNavigationBarItemType

class MainNavigator(
    val navHostController: NavHostController
) {
    val startDestination = SignInRoute.ROUTE

    private val currentDestination: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination

    val currentMainNavigationBarItem: MainNavigationBarItemType?
        @Composable get() = MainNavigationBarItemType.find { mainNavigationBarRoute ->
            currentDestination?.route == mainNavigationBarRoute::class.simpleName
        }

    @Composable
    fun showBottomBar(): Boolean = MainNavigationBarItemType.contains {
        currentDestination?.route == it::class.simpleName
    }

    fun navigateMainNavigation(mainNavigationBarItemType: MainNavigationBarItemType) {
        navOptions {
            popUpTo(MainNavigationBarRoute.Home::class.simpleName.orEmpty()) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }.let { navOptions ->
            when (mainNavigationBarItemType) {
                MainNavigationBarItemType.HOME -> navHostController.navigationHome(navOptions)
                MainNavigationBarItemType.DIARY -> navHostController.navigationDiary(navOptions)
                MainNavigationBarItemType.MY_PAGE -> navHostController.navigationMyPage(navOptions)
            }
        }
    }

    fun navigateToHome(navOptions: NavOptions? = null) {
        navHostController.navigationHome(
            navOptions ?: navOptions {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        )
    }
}

@Composable
fun rememberMainNavigator(
    navHostController: NavHostController = rememberNavController()
): MainNavigator = remember(navHostController) {
    MainNavigator(navHostController = navHostController)
}