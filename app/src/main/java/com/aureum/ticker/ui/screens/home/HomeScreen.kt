package com.aureum.ticker.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aureum.ticker.ui.components.AureumSearchBar
import com.aureum.ticker.ui.components.ShimmerTickerList
import com.aureum.ticker.ui.components.TickerCard
import com.aureum.ticker.ui.screens.search.SearchOverlay
import com.aureum.ticker.ui.theme.AureumGold
import com.aureum.ticker.ui.theme.AureumTheme
import com.aureum.ticker.ui.theme.MonoFamily

@Composable
fun HomeScreen(
    onTickerClick: (symbol: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSearch by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(24.dp))

            // Logo
            Text(
                text = "A U R E U M",
                fontFamily = MonoFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = AureumGold,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(24.dp))

            // Tappable search bar (opens overlay)
            AureumSearchBar(
                query = "",
                onQueryChange = {},
                onClear = {},
                enabled = false,
                modifier = Modifier.clickable { showSearch = true },
            )

            Spacer(Modifier.height(28.dp))

            // Section header
            Text(
                text = "Trending",
                style = MaterialTheme.typography.headlineLarge,
                color = AureumTheme.colors.textPrimary,
            )

            Spacer(Modifier.height(16.dp))

            // Ticker list
            if (uiState.isLoading) {
                ShimmerTickerList()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 32.dp),
                ) {
                    items(
                        items = uiState.trendingTickers,
                        key = { it.symbol },
                    ) { ticker ->
                        TickerCard(
                            ticker = ticker,
                            onClick = { onTickerClick(ticker.symbol) },
                        )
                    }
                }
            }

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = AureumTheme.colors.negative,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }

        // Search overlay (renders on top)
        SearchOverlay(
            visible = showSearch,
            onDismiss = { showSearch = false },
            onTickerSelected = { symbol ->
                showSearch = false
                onTickerClick(symbol)
            },
        )
    }
}
