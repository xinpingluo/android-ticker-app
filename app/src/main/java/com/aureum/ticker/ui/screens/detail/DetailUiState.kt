package com.aureum.ticker.ui.screens.detail

import com.aureum.ticker.data.model.PricePoint
import com.aureum.ticker.data.model.TickerDetail
import com.aureum.ticker.data.model.TimeRange

data class DetailUiState(
    val ticker: TickerDetail? = null,
    val priceHistory: List<PricePoint> = emptyList(),
    val selectedRange: TimeRange = TimeRange.ONE_DAY,
    val isLoading: Boolean = true,
    val error: String? = null,
)
