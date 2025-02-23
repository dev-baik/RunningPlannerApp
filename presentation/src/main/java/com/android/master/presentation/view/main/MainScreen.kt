package com.android.master.presentation.view.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.master.presentation.utils.NavigationUtils
import com.android.master.presentation.view.NavigationItem
import com.android.master.presentation.view.NavigationRouteName
import com.android.master.presentation.view.Temp
import com.android.master.presentation.view.diary.DiaryScreen
import com.android.master.presentation.view.home.HomeScreen
import com.android.master.presentation.view.myPage.MyPageScreen
import com.android.master.presentation.view.temp.TempScreen
import com.android.master.presentation.view.video.VideoScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scaffoldState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = RoundedCornerShape(10.dp),
                )
            }
        },
        bottomBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(navController, currentRoute)
            }
        }
    ) { paddingValues ->
        MainNavigationScreen(
            modifier = Modifier.padding(paddingValues),
            scaffoldState = scaffoldState,
            navController = navController,
        )
    }
}

@Composable
fun MainBottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val bottomNavigationItems = listOf(
        NavigationItem.MainNav.Home,
        NavigationItem.MainNav.Diary,
        NavigationItem.MainNav.MyPage
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route,
                        modifier = Modifier.size(28.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    NavigationUtils.navigate(
                        navController,
                        item.route,
                        navController.graph.startDestinationRoute
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
            )
        }
    }
}

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
    scaffoldState: SnackbarHostState,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRouteName.MAIN_HOME,
        modifier = modifier
    ) {
        composable(NavigationRouteName.MAIN_HOME) {
            HomeScreen(navController)
        }
        composable(NavigationRouteName.MAIN_DIARY) {
            DiaryScreen()
        }
        composable(NavigationRouteName.MAIN_MY_PAGE) {
            MyPageScreen(scaffoldState)
        }
        composable(NavigationRouteName.VIDEO) {
            VideoScreen(navController)
        }
        composable(
            route = Temp.routeWithArgName(),
            arguments = Temp.arguments,
        ) {
            val tempItem = Temp.findArgument(it)
            if (tempItem != null) {
                TempScreen(tempItem = tempItem)
            }
        }
    }
}