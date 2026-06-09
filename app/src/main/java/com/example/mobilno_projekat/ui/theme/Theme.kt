package com.example.mobilno_projekat.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenContainerStrong,
    onPrimaryContainer = GreenPrimaryDark,
    secondary = GreenAccent,
    onSecondary = Color.White,
    secondaryContainer = GreenContainer,
    onSecondaryContainer = GreenPrimaryDark,
    background = GreenSurface,
    onBackground = GreenText,
    surface = GreenSurfaceSoft,
    onSurface = GreenText,
    surfaceVariant = GreenSurfaceCard,
    onSurfaceVariant = GreenTextMuted,
    error = ErrorRed,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenContainerStrong,
    onPrimary = DarkGreenBackground,
    primaryContainer = DarkGreenContainer,
    onPrimaryContainer = DarkGreenText,
    secondary = GreenAccent,
    onSecondary = Color.White,
    secondaryContainer = DarkGreenCard,
    onSecondaryContainer = DarkGreenText,
    background = DarkGreenBackground,
    onBackground = DarkGreenText,
    surface = DarkGreenSurface,
    onSurface = DarkGreenText,
    surfaceVariant = DarkGreenCard,
    onSurfaceVariant = DarkGreenText,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(30.dp)
)

@Composable
fun MobilnoProjekatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}