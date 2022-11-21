package com.tmhwrd.weather.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = MRed,
    primaryVariant = MRed,
    onPrimary = White,
    secondary = Gray,
    secondaryVariant = Gray,
    onSecondary = Black
)

private val LightColorPalette = lightColors(
    primary = MRed,
    primaryVariant = MRed,
    onPrimary = White,
    secondary = Gray,
    secondaryVariant = Gray,
    onSecondary = Black
)

@Composable
fun WeatherTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
//        shapes = shapes,
        content = content
    )
}
