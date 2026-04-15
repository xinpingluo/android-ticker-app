package com.aureum.ticker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aureum.ticker.data.model.Ticker
import com.aureum.ticker.ui.theme.AureumTheme
import com.aureum.ticker.ui.theme.TextSecondary

@Composable
fun TickerCard(
    ticker: Ticker,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = AureumTheme.colors.surface,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left: Symbol + Company Name
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ticker.symbol,
                    style = MaterialTheme.typography.titleLarge,
                    color = AureumTheme.colors.textPrimary,
                )
                Text(
                    text = ticker.companyName,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 1,
                )
            }

            // Center: Sparkline
            SparklineChart(
                points = ticker.sparklinePoints,
                isPositive = ticker.changeAmount >= 0,
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp),
            )

            Spacer(Modifier.width(16.dp))

            // Right: Price + Change
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {
                PriceText(
                    price = ticker.price,
                    style = MaterialTheme.typography.titleMedium,
                )
                ChangeIndicator(
                    changeAmount = ticker.changeAmount,
                    changePercent = ticker.changePercent,
                    showAmount = false,
                )
            }
        }
    }
}
