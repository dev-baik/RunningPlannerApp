package com.android.master.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.android.master.presentation.R

val SuitMedium = FontFamily(Font(R.font.suit_medium))
val SuitRegular = FontFamily(Font(R.font.suit_regular))
val SuitSemiBold = FontFamily(Font(R.font.suit_semibold))

@Immutable
data class RPAppTypography(
    // Title
    val titleSemi24: TextStyle,
    val titleSemi20: TextStyle,
    val titleSemi16: TextStyle,
    val titleSemi14: TextStyle,
    val titleSemi12: TextStyle,

    // Body
    val bodyMed16: TextStyle,
    val bodyReg16: TextStyle,
    val bodyMed14: TextStyle,
    val bodyReg14: TextStyle,
    val bodyMed12: TextStyle,

    // Caption
    val capReg16: TextStyle,
    val capReg12: TextStyle,
    val capSemi10: TextStyle,
    val capMed10: TextStyle,
    val capReg10: TextStyle
)

val defaultRPAppTypography = RPAppTypography(
    // Title
    titleSemi24 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.6
    ),
    titleSemi20 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.6,
    ),
    titleSemi16 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6
    ),
    titleSemi14 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6
    ),
    titleSemi12 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6
    ),

    // Body
    bodyMed16 = TextStyle(
        fontFamily = SuitMedium,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6
    ),
    bodyReg16 = TextStyle(
        fontFamily = SuitRegular,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6
    ),
    bodyMed14 = TextStyle(
        fontFamily = SuitMedium,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6
    ),
    bodyReg14 = TextStyle(
        fontFamily = SuitRegular,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6
    ),
    bodyMed12 = TextStyle(
        fontFamily = SuitMedium,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6
    ),

    // Caption
    capReg16 = TextStyle(
        fontFamily = SuitRegular,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6
    ),
    capReg12 = TextStyle(
        fontFamily = SuitRegular,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6
    ),
    capSemi10 = TextStyle(
        fontFamily = SuitSemiBold,
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.6
    ),
    capMed10 = TextStyle(
        fontFamily = SuitMedium,
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.6
    ),
    capReg10 = TextStyle(
        fontFamily = SuitRegular,
        fontSize = 10.sp,
        lineHeight = 10.sp * 1.6
    ),
)

val LocalRPAppTypography = staticCompositionLocalOf { defaultRPAppTypography }