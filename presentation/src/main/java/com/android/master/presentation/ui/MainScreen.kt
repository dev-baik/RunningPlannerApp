package com.android.master.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.master.presentation.ui.home.DiaryScreen
import com.android.master.presentation.ui.home.HomeScreen
import com.android.master.presentation.ui.home.MyPageScreen
import com.android.master.presentation.ui.theme.RunningPlannerAppTheme
import com.android.master.presentation.utils.NavigationUtils
import com.android.master.presentation.viewmodel.MainViewModel

@Preview(showBackground = true)
@Composable
fun Preview() {
    RunningPlannerAppTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
            MainBottomNavigationBar(navController, currentRoute)
        }
    }) { paddingValues ->
        MainNavigationScreen(
            viewModel = mainViewModel,
            navController = navController,
            modifier = Modifier.padding(paddingValues)
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
    viewModel: MainViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRouteName.MAIN_HOME,
        modifier = modifier
    ) {
        composable(NavigationRouteName.MAIN_HOME) {
            HomeScreen(viewModel)
        }
        composable(NavigationRouteName.MAIN_DIARY) {
            DiaryScreen(viewModel)
        }
        composable(NavigationRouteName.MAIN_MY_PAGE) {
            MyPageScreen(viewModel)
        }
    }
}