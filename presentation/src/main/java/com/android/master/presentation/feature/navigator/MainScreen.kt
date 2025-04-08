package com.android.master.presentation.feature.navigator

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.master.presentation.feature.navigator.component.MainBottomBar
import com.android.master.presentation.feature.navigator.component.MainNavHost
import com.android.master.presentation.type.MainNavigationBarItemType
import com.android.master.presentation.ui.component.view.RPAppSnackBar
import com.android.master.presentation.ui.theme.RPAPPTheme

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