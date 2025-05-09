package com.android.master.presentation.feature.splash

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.android.master.presentation.R
import com.android.master.presentation.ui.theme.Blue
import com.android.master.presentation.ui.theme.RPAPPTheme
import com.android.master.presentation.ui.theme.RPAppTheme

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

    LaunchedEffect(Unit) {
        window?.let {
            setSystemBarStyle(
                window = window,
                view = view,
                isLightTheme = false,
                barColor = Blue.toArgb(),
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
        Spacer(modifier = Modifier.weight(226f / (226f + 284f)))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.splash_logo_description)
        )
        Text(
            text = stringResource(R.string.splash_title),
            color = RPAppTheme.colors.white,
            style = RPAppTheme.typography.titleSemi24
        )
        Spacer(modifier = Modifier.weight(284f / (226f + 284f)))
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