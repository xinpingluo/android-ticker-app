package com.aureum.ticker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aureum.ticker.ui.theme.AureumTheme
import com.aureum.ticker.util.formatChange
import com.aureum.ticker.util.formatPercent

@Composable
fun ChangeIndicator(
    changeAmount: Double,
    changePercent: Double,
    modifier: Modifier = Modifier,
    showAmount: Boolean = true,
) {
    val isPositive = changeAmount >= 0
    val color = if (isPositive) AureumTheme.colors.positive else AureumTheme.colors.negative
    val icon = if (isPositive) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isPositive) "Up" else "Down",
            tint = color,
            modifier = Modifier.size(18.dp),
        )
        if (showAmount) {
            Text(
                text = changeAmount.formatChange(),
                style = MaterialTheme.typography.labelLarge,
                color = color,
            )
            Spacer(Modifier.width(4.dp))
        }
        Text(
            text = "(${changePercent.formatPercent()})",
            style = MaterialTheme.typography.labelLarge,
            color = color,
        )
    }
}
