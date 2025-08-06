package com.cavemanproductivity.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CavemanLightColorScheme = lightColorScheme(
    primary = SaddleBrown,
    onPrimary = AntiqueWhite,
    primaryContainer = BurlyWood,
    onPrimaryContainer = DarkSlateGray,
    secondary = Chocolate,
    onSecondary = AntiqueWhite,
    secondaryContainer = SandyBrown,
    onSecondaryContainer = DarkSlateGray,
    tertiary = Tomato,
    onTertiary = AntiqueWhite,
    tertiaryContainer = RosyBrown,
    onTertiaryContainer = DarkSlateGray,
    error = OrangeRed,
    onError = AntiqueWhite,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Wheat,
    onBackground = DarkSlateGray,
    surface = BurlyWood,
    onSurface = DarkSlateGray,
    surfaceVariant = StoneMedium,
    onSurfaceVariant = DarkSlateGray,
    outline = Sienna,
    outlineVariant = StoneDark,
    scrim = Color(0xFF000000),
    inverseSurface = DarkSlateGray,
    inverseOnSurface = AntiqueWhite,
    inversePrimary = SandyBrown
)

private val CavemanDarkColorScheme = darkColorScheme(
    primary = Chocolate,
    onPrimary = DarkSlateGray,
    primaryContainer = Sienna,
    onPrimaryContainer = AntiqueWhite,
    secondary = SandyBrown,
    onSecondary = DarkSlateGray,
    secondaryContainer = Color(0xFF5D4037),
    onSecondaryContainer = AntiqueWhite,
    tertiary = Tomato,
    onTertiary = DarkSlateGray,
    tertiaryContainer = Color(0xFF8C4A47),
    onTertiaryContainer = AntiqueWhite,
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1A1A),
    onBackground = AntiqueWhite,
    surface = Color(0xFF2D2D2D),
    onSurface = AntiqueWhite,
    surfaceVariant = Color(0xFF3E2723),
    onSurfaceVariant = AntiqueWhite,
    outline = Color(0xFF8D6E63),
    outlineVariant = Color(0xFF5D4037),
    scrim = Color(0xFF000000),
    inverseSurface = AntiqueWhite,
    inverseOnSurface = DarkSlateGray,
    inversePrimary = SaddleBrown
)

@Composable
fun CavemanProductivityTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        CavemanDarkColorScheme
    } else {
        CavemanLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CavemanTypography,
        shapes = CavemanShapes,
        content = content
    )
}