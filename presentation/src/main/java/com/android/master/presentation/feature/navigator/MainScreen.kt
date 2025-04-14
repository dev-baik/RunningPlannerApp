package com.android.master.presentation.feature.navigator

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.android.master.presentation.feature.navigator.component.MainBottomBar
import com.android.master.presentation.feature.navigator.component.MainNavHost
import com.android.master.presentation.type.MainNavigationBarItemType
import com.android.master.presentation.ui.component.view.RPAppSnackBar
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.White

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RPAPPTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    mainState: MainState = rememberMainState()
) {
    val view by rememberUpdatedState(LocalView.current)
    val window = remember { (view.context as? ComponentActivity)?.window }

    LaunchedEffect(key1 = Unit) {
        window?.let {
            setSystemBarStyle(
                window = window,
                view = view,
                isLightTheme = true,
                barColor = White.toArgb(),
            )
        }
    }

    MainScreenContent(
        navigator = navigator,
        mainState = mainState
    )
}

@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    mainState: MainState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                padding = padding,
                onShowSnackbar = { message, duration ->
                    mainState.showSnackbar(
                        message = message,
                        duration = duration
                    )
                }
            )
        },
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                navigationBarItems = MainNavigationBarItemType.entries.toList(),
                onNavigationBarItemSelected = { navigator.navigateMainNavigation(it) },
                currentNavigationBarItem = navigator.currentMainNavigationBarItem
            )
        },
        snackbarHost = { RPAppSnackBar(snackBarHostState = mainState.snackbarHostState) },
    )
}

private fun setSystemBarStyle(
    window: Window?,
    view: View,
    isLightTheme: Boolean,
    barColor: Int,
) {
    window?.let {
        val controller = WindowCompat.getInsetsController(it, view)
        controller.isAppearanceLightStatusBars = isLightTheme
        controller.isAppearanceLightNavigationBars = isLightTheme

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            it.statusBarColor = barColor
            it.navigationBarColor = barColor
        }
    }
}