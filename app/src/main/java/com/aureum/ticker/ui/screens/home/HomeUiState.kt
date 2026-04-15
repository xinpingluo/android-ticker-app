package com.aureum.ticker.ui.screens.home

import com.aureum.ticker.data.model.Ticker

data class HomeUiState(
    val trendingTickers: List<Ticker> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)
