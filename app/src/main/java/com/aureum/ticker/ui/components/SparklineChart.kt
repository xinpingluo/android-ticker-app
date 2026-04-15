package com.aureum.ticker.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aureum.ticker.ui.theme.TickerGreen
import com.aureum.ticker.ui.theme.TickerGreenDim
import com.aureum.ticker.ui.theme.TickerRed
import com.aureum.ticker.ui.theme.TickerRedDim

@Composable
fun SparklineChart(
    points: List<Float>,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
    lineColor: Color = if (isPositive) TickerGreen else TickerRed,
    fillColor: Color = if (isPositive) TickerGreenDim else TickerRedDim,
    strokeWidth: Dp = 1.5.dp,
    animate: Boolean = false,
) {
    if (points.size < 2) return

    val animationProgress = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(points) {
        if (animate) {
            animationProgress.snapTo(0f)
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(800, easing = EaseInOutCubic),
            )
        }
    }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val minVal = points.min()
        val maxVal = points.max()
        val range = (maxVal - minVal).coerceAtLeast(0.01f)

        val stepX = width / (points.size - 1)
        val coords = points.mapIndexed { i, value ->
            Offset(
                x = i * stepX,
                y = height - ((value - minVal) / range) * height * 0.85f - height * 0.075f,
            )
        }

        // Build smooth path with cubic bezier
        val linePath = Path().apply {
            moveTo(coords[0].x, coords[0].y)
            for (i in 1 until coords.size) {
                val prev = coords[i - 1]
                val curr = coords[i]
                val cpx = (prev.x + curr.x) / 2
                cubicTo(cpx, prev.y, cpx, curr.y, curr.x, curr.y)
            }
        }

        // Animate: draw only the visible portion of the path
        val progress = animationProgress.value
        val pathToDraw = if (progress < 1f) {
            val measure = PathMeasure()
            measure.setPath(linePath, false)
            Path().also {
                measure.getSegment(0f, measure.length * progress, it, true)
            }
        } else {
            linePath
        }

        // Fill gradient below the line
        if (progress >= 1f) {
            val fillPath = Path().apply {
                addPath(linePath)
                lineTo(coords.last().x, height)
                lineTo(coords.first().x, height)
                close()
            }
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(fillColor, Color.Transparent),
                    startY = 0f,
                    endY = height,
                ),
            )
        }

        // Draw the line
        drawPath(
            path = pathToDraw,
            color = lineColor,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
            ),
        )
    }
}
