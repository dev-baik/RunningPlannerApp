package com.android.master.presentation.feature.navigator

import androidx.compose.runtime.Composable
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
fun MainScreen() {
    MainScreenContent()
}

@Composable
private fun MainScreenContent() {

}
