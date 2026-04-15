package com.aureum.ticker.data.repository

import com.aureum.ticker.data.model.PricePoint
import com.aureum.ticker.data.model.Ticker
import com.aureum.ticker.data.model.TickerDetail
import com.aureum.ticker.data.model.TimeRange
import com.aureum.ticker.data.remote.MockDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TickerRepositoryImpl @Inject constructor() : TickerRepository {

    override suspend fun getTrendingTickers(): List<Ticker> = withContext(Dispatchers.IO) {
        delay(300) // Simulate network latency
        MockDataSource.getTrendingTickers()
    }

    override suspend fun searchTickers(query: String): List<Ticker> = withContext(Dispatchers.IO) {
        delay(200)
        MockDataSource.searchTickers(query)
    }

    override suspend fun getTickerDetail(symbol: String): TickerDetail? = withContext(Dispatchers.IO) {
        delay(250)
        MockDataSource.getTickerDetail(symbol)
    }

    override suspend fun getPriceHistory(symbol: String, range: TimeRange): List<PricePoint> =
        withContext(Dispatchers.IO) {
            delay(200)
            val pointCount = when (range) {
                TimeRange.ONE_DAY -> 50
                TimeRange.ONE_WEEK -> 70
                TimeRange.ONE_MONTH -> 90
                TimeRange.THREE_MONTHS -> 120
                TimeRange.ONE_YEAR -> 150
                TimeRange.ALL -> 200
            }
            MockDataSource.getPriceHistory(symbol, pointCount)
        }
}
