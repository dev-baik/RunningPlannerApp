package com.android.master.presentation.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    navigator: MainNavigator = rememberMainNavigator()
) {
    MainScreenContent(
        navigator = navigator
    )
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator
) {

}