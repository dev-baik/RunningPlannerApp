package com.android.master.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object RPAppTheme {
    val colors: RPAppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalRPAppColors.current

    val typography: RPAppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalRPAppTypography.current
}

@Composable
fun ProvideRPAppColorsAndTypography(
    colors: RPAppColors,
    typography: RPAppTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalRPAppColors provides colors,
        LocalRPAppTypography provides typography,
        content = content
    )
}

@Composable
fun RPAPPTheme(
    content: @Composable () -> Unit
) {
    ProvideRPAppColorsAndTypography(
        colors = defaultRPAppColors,
        typography = defaultRPAppTypography
    ) {
        MaterialTheme(
            content = content
        )
    }
}