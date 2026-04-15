package com.aureum.ticker.data.repository

import com.aureum.ticker.data.model.PricePoint
import com.aureum.ticker.data.model.Ticker
import com.aureum.ticker.data.model.TickerDetail
import com.aureum.ticker.data.model.TimeRange

interface TickerRepository {
    suspend fun getTrendingTickers(): List<Ticker>
    suspend fun searchTickers(query: String): List<Ticker>
    suspend fun getTickerDetail(symbol: String): TickerDetail?
    suspend fun getPriceHistory(symbol: String, range: TimeRange): List<PricePoint>
}
