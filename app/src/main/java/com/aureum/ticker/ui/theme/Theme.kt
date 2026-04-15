package com.aureum.ticker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val DarkColorScheme = darkColorScheme(
    background = AureumBlack,
    surface = AureumSurface,
    surfaceVariant = AureumSurfaceHigh,
    primary = AureumGold,
    onPrimary = TextOnGold,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    error = TickerRed,
    outline = SearchBarBorder,
    outlineVariant = DividerColor,
)

object AureumTheme {
    val colors: AureumColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalAureumColors.current
}

@Composable
fun AureumTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAureumColors provides AureumColorScheme()) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = AureumTypography,
            shapes = AureumShapes,
            content = content,
        )
    }
}
