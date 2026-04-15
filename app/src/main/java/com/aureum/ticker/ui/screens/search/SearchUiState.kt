package com.aureum.ticker.ui.screens.search

import com.aureum.ticker.data.model.Ticker

data class SearchUiState(
    val results: List<Ticker> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
)
