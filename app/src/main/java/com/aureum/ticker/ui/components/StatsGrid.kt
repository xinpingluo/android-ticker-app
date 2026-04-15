package com.aureum.ticker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aureum.ticker.ui.theme.AureumTheme

data class StatItem(val label: String, val value: String)

@Composable
fun StatsGrid(
    stats: List<StatItem>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        val rows = stats.chunked(2)
        rows.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                pair.forEach { item ->
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = AureumTheme.colors.textTertiary,
                        )
                        Text(
                            text = item.value,
                            style = MaterialTheme.typography.labelLarge,
                            color = AureumTheme.colors.textPrimary,
                        )
                    }
                }
            }
            if (index < rows.lastIndex) {
                HorizontalDivider(color = AureumTheme.colors.divider, thickness = 0.5.dp)
            }
        }
    }
}
