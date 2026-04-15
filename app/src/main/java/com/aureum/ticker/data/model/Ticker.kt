package com.aureum.ticker.data.model

data class Ticker(
    val symbol: String,
    val companyName: String,
    val price: Double,
    val changeAmount: Double,
    val changePercent: Double,
    val sparklinePoints: List<Float>,
)
