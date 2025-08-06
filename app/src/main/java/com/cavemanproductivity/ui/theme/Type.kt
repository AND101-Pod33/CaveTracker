package com.cavemanproductivity.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Fallback to system fonts with rough styling for now
// TODO: Add custom fonts to res/font/ directory for full caveman aesthetic
val CavemanFontFamily = FontFamily.Default
val RoughFontFamily = FontFamily.Default

val CavemanTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = SaddleBrown
    ),
    displayMedium = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = SaddleBrown
    ),
    displaySmall = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = SaddleBrown
    ),
    headlineLarge = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = Chocolate
    ),
    headlineMedium = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = Chocolate
    ),
    headlineSmall = TextStyle(
        fontFamily = CavemanFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Chocolate
    ),
    titleLarge = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = DarkSlateGray
    ),
    titleMedium = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = DarkSlateGray
    ),
    titleSmall = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = DarkSlateGray
    ),
    bodyLarge = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = DarkSlateGray
    ),
    bodyMedium = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = DarkSlateGray
    ),
    bodySmall = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = DarkSlateGray
    ),
    labelLarge = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = DarkSlateGray
    ),
    labelMedium = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = DarkSlateGray
    ),
    labelSmall = TextStyle(
        fontFamily = RoughFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = DarkSlateGray
    )
)