package com.aureum.ticker.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aureum.ticker.ui.theme.AureumSurface
import com.aureum.ticker.ui.theme.AureumSurfaceHigh

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    height: Dp = 72.dp,
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateX = transition.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(AureumSurface, AureumSurfaceHigh, AureumSurface),
        start = Offset(translateX.value, 0f),
        end = Offset(translateX.value + 200f, 0f),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(shimmerBrush),
    )
}

@Composable
fun ShimmerTickerList(count: Int = 5) {
    repeat(count) {
        ShimmerPlaceholder(
            modifier = Modifier.fillMaxWidth(),
            height = 72.dp,
        )
        Spacer(Modifier.height(12.dp))
    }
}
