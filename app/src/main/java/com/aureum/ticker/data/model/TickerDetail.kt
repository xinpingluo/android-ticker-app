package com.aureum.ticker.data.model

data class TickerDetail(
    val symbol: String,
    val companyName: String,
    val price: Double,
    val changeAmount: Double,
    val changePercent: Double,
    val open: Double,
    val high: Double,
    val low: Double,
    val volume: Long,
    val marketCap: Long,
    val peRatio: Double?,
    val high52w: Double,
    val low52w: Double,
)
