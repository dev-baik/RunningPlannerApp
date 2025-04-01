package com.android.master.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

// Secondary
val Pink = Color(0xFFF47AFF)

// Tertiary
val Blue = Color(0xFF3F75FF)
val Green = Color(0xFF34C18E)

// GrayScale
val Gray100 = Color(0xFFF6F7F9)
val Gray200 = Color(0xFFEBEFF3)
val Gray300 = Color(0xFFDEE3E8)
val Gray400 = Color(0xFFBBC0C5)
val Gray500 = Color(0xFF9DA1A6)
val Gray600 = Color(0xFF74787C)
val Gray700 = Color(0xFF606468)
val Gray800 = Color(0xFF414549)
val Gray900 = Color(0xFF242527)

// Error
val Error = Color(0xFFEB0555)

@Immutable
data class RPAppColors(
    // Primary
    val black: Color,
    val white: Color,

    // secondary
    val pink: Color,

    // tertiary
    val blue: Color,
    val green: Color,

    // GrayScale
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,

    // Error
    val error: Color
)

val defaultRPAppColors = RPAppColors(
    // Primary
    black = Black,
    white = White,

    // Secondary
    pink = Pink,

    // tertiary
    blue = Blue,
    green = Green,

    // GrayScale
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray900 = Gray900,

    // Error
    error = Error
)

val LocalRPAppColors = staticCompositionLocalOf { defaultRPAppColors }