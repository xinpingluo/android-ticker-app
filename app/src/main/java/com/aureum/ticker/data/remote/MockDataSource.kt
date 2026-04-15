package com.aureum.ticker.data.remote

import com.aureum.ticker.data.model.PricePoint
import com.aureum.ticker.data.model.Ticker
import com.aureum.ticker.data.model.TickerDetail
import kotlin.math.sin
import kotlin.random.Random

object MockDataSource {

    private val tickers = listOf(
        TickerSeed("AAPL", "Apple Inc.", 187.44, 2.31, 1.25, 185.13, 188.20, 184.90, 52_300_000, 2_910_000_000_000, 29.4, 199.62, 164.08),
        TickerSeed("GOOGL", "Alphabet Inc.", 141.80, -0.45, -0.32, 142.25, 143.10, 141.05, 28_100_000, 1_780_000_000_000, 25.1, 153.78, 115.35),
        TickerSeed("MSFT", "Microsoft Corp.", 415.60, 3.68, 0.89, 411.92, 416.80, 410.50, 21_500_000, 3_090_000_000_000, 36.2, 430.82, 309.45),
        TickerSeed("AMZN", "Amazon.com Inc.", 178.25, 1.90, 1.08, 176.35, 179.10, 175.80, 45_200_000, 1_860_000_000_000, 62.8, 189.50, 118.35),
        TickerSeed("TSLA", "Tesla Inc.", 248.50, 8.92, 3.72, 239.58, 250.10, 238.20, 112_000_000, 790_000_000_000, 72.5, 299.29, 152.37),
        TickerSeed("NVDA", "NVIDIA Corp.", 875.30, 18.40, 2.15, 856.90, 880.50, 852.10, 38_400_000, 2_160_000_000_000, 65.3, 974.00, 392.30),
        TickerSeed("META", "Meta Platforms Inc.", 505.75, -2.10, -0.41, 507.85, 510.30, 503.20, 16_800_000, 1_300_000_000_000, 28.9, 542.81, 274.38),
        TickerSeed("NFLX", "Netflix Inc.", 628.90, 5.25, 0.84, 623.65, 632.10, 621.50, 8_200_000, 272_000_000_000, 47.6, 639.00, 344.73),
        TickerSeed("AMD", "Advanced Micro Devices", 162.35, -1.80, -1.10, 164.15, 165.20, 161.50, 52_100_000, 262_000_000_000, 46.8, 227.30, 93.12),
        TickerSeed("JPM", "JPMorgan Chase & Co.", 198.40, 1.25, 0.63, 197.15, 199.80, 196.50, 9_800_000, 571_000_000_000, 11.8, 205.88, 135.19),
    )

    fun getTrendingTickers(): List<Ticker> = tickers.map { it.toTicker() }

    fun searchTickers(query: String): List<Ticker> {
        val q = query.uppercase()
        return tickers.filter {
            it.symbol.contains(q) || it.name.uppercase().contains(q)
        }.map { it.toTicker() }
    }

    fun getTickerDetail(symbol: String): TickerDetail? =
        tickers.find { it.symbol == symbol }?.toDetail()

    fun getPriceHistory(symbol: String, pointCount: Int = 50): List<PricePoint> {
        val seed = tickers.find { it.symbol == symbol } ?: return emptyList()
        val basePrice = seed.price
        val now = System.currentTimeMillis()
        val interval = 5 * 60 * 1000L // 5 min intervals
        val random = Random(symbol.hashCode())
        return List(pointCount) { i ->
            val noise = sin(i * 0.3) * basePrice * 0.02 + (random.nextDouble() - 0.5) * basePrice * 0.01
            PricePoint(
                timestamp = now - (pointCount - i) * interval,
                price = (basePrice + noise).toFloat(),
            )
        }
    }

    private fun generateSparkline(price: Double, positive: Boolean): List<Float> {
        val random = Random(price.hashCode())
        val trend = if (positive) 0.003 else -0.003
        var current = price * 0.98
        return List(20) {
            current += current * (trend + (random.nextDouble() - 0.48) * 0.015)
            current.toFloat()
        }
    }

    private data class TickerSeed(
        val symbol: String,
        val name: String,
        val price: Double,
        val changeAmount: Double,
        val changePercent: Double,
        val open: Double,
        val high: Double,
        val low: Double,
        val volume: Long,
        val marketCap: Long,
        val peRatio: Double,
        val high52w: Double,
        val low52w: Double,
    ) {
        fun toTicker() = Ticker(
            symbol = symbol,
            companyName = name,
            price = price,
            changeAmount = changeAmount,
            changePercent = changePercent,
            sparklinePoints = generateSparkline(price, changeAmount >= 0),
        )

        fun toDetail() = TickerDetail(
            symbol = symbol,
            companyName = name,
            price = price,
            changeAmount = changeAmount,
            changePercent = changePercent,
            open = open,
            high = high,
            low = low,
            volume = volume,
            marketCap = marketCap,
            peRatio = peRatio,
            high52w = high52w,
            low52w = low52w,
        )
    }
}
