package com.android.master.presentation.feature.splash

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.android.master.presentation.R
import com.android.master.presentation.ui.theme.Blue
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme
import com.android.master.presentation.ui.theme.White

@Preview
@Composable
fun SplashScreenPreview() {
    RPAPPTheme {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    val view by rememberUpdatedState(LocalView.current)
    val window = remember { (view.context as? ComponentActivity)?.window }

    LaunchedEffect(key1 = Unit) {
        window?.let {
            setSystemBarStyle(
                window = window,
                view = view,
                isLightTheme = false,
                barColor = Blue.toArgb(),
            )
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            setSystemBarStyle(
                window = window,
                view = view,
                isLightTheme = true,
                barColor = White.toArgb(),
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RPAppTheme.colors.blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.splash_title),
            color = RPAppTheme.colors.white,
            style = RPAppTheme.typography.titleSemi24
        )
    }
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