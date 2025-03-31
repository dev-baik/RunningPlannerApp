package com.android.master.presentation.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainNavigator(
    val navHostController: NavHostController
) {

}

@Composable
fun rememberMainNavigator(
    navHostController: NavHostController = rememberNavController()
): MainNavigator = remember(navHostController) {
    MainNavigator(navHostController = navHostController)
}