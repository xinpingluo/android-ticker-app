package com.aureum.ticker.ui.screens.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aureum.ticker.ui.components.AureumSearchBar
import com.aureum.ticker.ui.components.ShimmerPlaceholder
import com.aureum.ticker.ui.components.TickerCard
import com.aureum.ticker.ui.theme.AureumTheme

@Composable
fun SearchOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    onTickerSelected: (symbol: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    BackHandler(enabled = visible) {
        viewModel.clearQuery()
        onDismiss()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(200)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AureumTheme.colors.overlay)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    viewModel.clearQuery()
                    onDismiss()
                },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { /* Prevent dismiss when tapping content */ },
            ) {
                Spacer(Modifier.height(24.dp))

                // Search bar - slides in from above
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(200)) + slideInVertically(
                        animationSpec = tween(300, easing = EaseOutCubic),
                        initialOffsetY = { -it / 4 },
                    ),
                ) {
                    AureumSearchBar(
                        query = query,
                        onQueryChange = viewModel::onQueryChange,
                        onClear = { viewModel.clearQuery() },
                        enabled = true,
                        focusRequester = focusRequester,
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Loading state
                if (uiState.isSearching) {
                    repeat(3) {
                        ShimmerPlaceholder(height = 72.dp)
                        Spacer(Modifier.height(12.dp))
                    }
                }

                // Results with staggered fade-in
                if (uiState.results.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        itemsIndexed(
                            items = uiState.results,
                            key = { _, ticker -> "${query}_${ticker.symbol}" },
                        ) { index, ticker ->
                            val visibleState = remember(query, ticker.symbol) {
                                MutableTransitionState(false).apply { targetState = true }
                            }
                            AnimatedVisibility(
                                visibleState = visibleState,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 250,
                                        delayMillis = 100 + index * 60,
                                    )
                                ) + slideInVertically(
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        delayMillis = 100 + index * 60,
                                        easing = EaseOutCubic,
                                    ),
                                    initialOffsetY = { it / 3 },
                                ),
                            ) {
                                TickerCard(
                                    ticker = ticker,
                                    onClick = { onTickerSelected(ticker.symbol) },
                                )
                            }
                        }
                    }
                }

                // Empty state
                if (query.isNotEmpty() && !uiState.isSearching && uiState.results.isEmpty() && uiState.error == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "No tickers found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = AureumTheme.colors.textSecondary,
                        )
                    }
                }
            }
        }

        // Auto-focus the search bar when overlay appears
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
