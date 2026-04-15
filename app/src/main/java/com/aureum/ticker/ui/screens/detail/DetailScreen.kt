package com.aureum.ticker.ui.screens.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aureum.ticker.data.model.TimeRange
import com.aureum.ticker.ui.components.ChangeIndicator
import com.aureum.ticker.ui.components.PriceText
import com.aureum.ticker.ui.components.ShimmerPlaceholder
import com.aureum.ticker.ui.components.SparklineChart
import com.aureum.ticker.ui.components.StatItem
import com.aureum.ticker.ui.components.StatsGrid
import com.aureum.ticker.ui.theme.AureumGold
import com.aureum.ticker.ui.theme.AureumTheme
import com.aureum.ticker.ui.theme.TextOnGold
import com.aureum.ticker.util.formatCompact
import com.aureum.ticker.util.formatPrice

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(
    symbol: String,
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(scrollState),
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = AureumTheme.colors.textPrimary,
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = symbol,
                style = MaterialTheme.typography.titleLarge,
                color = AureumTheme.colors.textPrimary,
            )
        }

        if (uiState.isLoading) {
            Column(modifier = Modifier.padding(20.dp)) {
                ShimmerPlaceholder(height = 120.dp)
                Spacer(Modifier.height(16.dp))
                ShimmerPlaceholder(height = 200.dp)
                Spacer(Modifier.height(16.dp))
                ShimmerPlaceholder(height = 200.dp)
            }
            return
        }

        val ticker = uiState.ticker ?: return

        // Hero section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = ticker.companyName,
                style = MaterialTheme.typography.headlineMedium,
                color = AureumTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            PriceText(
                price = ticker.price,
                style = MaterialTheme.typography.displayLarge,
                color = AureumGold,
            )
            Spacer(Modifier.height(4.dp))
            ChangeIndicator(
                changeAmount = ticker.changeAmount,
                changePercent = ticker.changePercent,
            )
        }

        Spacer(Modifier.height(24.dp))

        // Time range selector
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TimeRange.entries.forEach { range ->
                val selected = uiState.selectedRange == range
                val containerColor by animateColorAsState(
                    targetValue = if (selected) AureumGold else AureumTheme.colors.surface,
                    animationSpec = tween(300),
                    label = "chipColor",
                )
                val labelColor by animateColorAsState(
                    targetValue = if (selected) TextOnGold else AureumTheme.colors.textSecondary,
                    animationSpec = tween(300),
                    label = "chipLabel",
                )
                FilterChip(
                    selected = selected,
                    onClick = { viewModel.selectTimeRange(range) },
                    label = {
                        Text(
                            text = range.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = labelColor,
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = containerColor,
                        selectedContainerColor = containerColor,
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = Color.Transparent,
                        selectedBorderColor = Color.Transparent,
                        enabled = true,
                        selected = selected,
                    ),
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Sparkline chart card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(containerColor = AureumTheme.colors.surface),
            shape = MaterialTheme.shapes.medium,
        ) {
            val chartPoints = uiState.priceHistory.map { it.price }
            if (chartPoints.size >= 2) {
                SparklineChart(
                    points = chartPoints,
                    isPositive = ticker.changeAmount >= 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp),
                    strokeWidth = 2.dp,
                    animate = true,
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Stats grid
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(containerColor = AureumTheme.colors.surface),
            shape = MaterialTheme.shapes.medium,
        ) {
            StatsGrid(
                stats = listOf(
                    StatItem("Open", ticker.open.formatPrice()),
                    StatItem("High", ticker.high.formatPrice()),
                    StatItem("Low", ticker.low.formatPrice()),
                    StatItem("Volume", ticker.volume.formatCompact()),
                    StatItem("Mkt Cap", ticker.marketCap.formatCompact()),
                    StatItem("P/E", ticker.peRatio?.let { "%.1f".format(it) } ?: "N/A"),
                    StatItem("52w High", ticker.high52w.formatPrice()),
                    StatItem("52w Low", ticker.low52w.formatPrice()),
                ),
                modifier = Modifier.padding(16.dp),
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}
