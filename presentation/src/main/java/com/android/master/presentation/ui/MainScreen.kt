package com.android.master.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.master.presentation.ui.theme.RunningPlannerAppTheme

sealed class MainNavigationItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Main : MainNavigationItem("Main", Icons.Default.Home, "홈")
    object Diary : MainNavigationItem("Diary", Icons.Default.DateRange, "다이어리")
    object MyPage : MainNavigationItem("MyPage", Icons.Default.AccountCircle, "마이페이지")
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    RunningPlannerAppTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        MainBottomNavigationBar(navController)
    }) { paddingValues ->
        MainNavigationScreen(
            navController,
            Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun MainBottomNavigationBar(navController: NavController) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Diary,
        MainNavigationItem.MyPage
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

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
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
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
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationItem.Main.route,
        modifier = modifier
    ) {
        composable(MainNavigationItem.Main.route) {
            Text(text = "Main")
        }
        composable(MainNavigationItem.Diary.route) {
            Text(text = "Diary")
        }
        composable(MainNavigationItem.MyPage.route) {
            Text(text = "MyPage")
        }
    }
}