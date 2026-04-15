package com.aureum.ticker.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.aureum.ticker.ui.theme.AureumGold
import com.aureum.ticker.util.formatPrice

@Composable
fun PriceText(
    price: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = AureumGold,
) {
    Text(
        text = price.formatPrice(),
        style = style,
        color = color,
        modifier = modifier,
    )
}
