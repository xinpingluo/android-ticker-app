package com.aureum.ticker.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ---- Backgrounds ----
val AureumBlack = Color(0xFF0D0D0D)
val AureumSurface = Color(0xFF1A1A1A)
val AureumSurfaceHigh = Color(0xFF242424)
val AureumOverlay = Color(0xCC000000)

// ---- Brand Accent ----
val AureumGold = Color(0xFFF5A623)
val AureumGoldDim = Color(0x66F5A623)
val AureumGoldSubtle = Color(0xFF3D2A0A)

// ---- Semantic ----
val TickerGreen = Color(0xFF00C853)
val TickerGreenDim = Color(0x4D00C853)
val TickerRed = Color(0xFFFF1744)
val TickerRedDim = Color(0x4DFF1744)

// ---- Text ----
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9E9E9E)
val TextTertiary = Color(0xFF616161)
val TextOnGold = Color(0xFF0D0D0D)

// ---- Misc ----
val DividerColor = Color(0xFF2C2C2C)
val SearchBarBg = Color(0xFF1E1E1E)
val SearchBarBorder = Color(0xFF333333)

@Immutable
data class AureumColorScheme(
    val background: Color = AureumBlack,
    val surface: Color = AureumSurface,
    val surfaceHigh: Color = AureumSurfaceHigh,
    val overlay: Color = AureumOverlay,
    val gold: Color = AureumGold,
    val goldDim: Color = AureumGoldDim,
    val goldSubtle: Color = AureumGoldSubtle,
    val positive: Color = TickerGreen,
    val positiveDim: Color = TickerGreenDim,
    val negative: Color = TickerRed,
    val negativeDim: Color = TickerRedDim,
    val textPrimary: Color = TextPrimary,
    val textSecondary: Color = TextSecondary,
    val textTertiary: Color = TextTertiary,
    val textOnGold: Color = TextOnGold,
    val divider: Color = DividerColor,
    val searchBarBg: Color = SearchBarBg,
    val searchBarBorder: Color = SearchBarBorder,
)

val LocalAureumColors = staticCompositionLocalOf { AureumColorScheme() }
