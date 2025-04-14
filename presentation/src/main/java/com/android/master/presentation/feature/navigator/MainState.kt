package com.android.master.presentation.feature.navigator

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class MainState(
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope
) {

    fun showSnackbar(message: String, duration: SnackbarDuration) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberMainState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainState = remember(snackbarHostState) {
    MainState(
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope
    )
}