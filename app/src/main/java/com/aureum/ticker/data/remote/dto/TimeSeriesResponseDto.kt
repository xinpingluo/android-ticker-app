package com.aureum.ticker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class TimeSeriesResponseDto(
    @Json(name = "Time Series (5min)") val timeSeries: Map<String, TimeSeriesEntryDto>?,
)

@JsonClass(generateAdapter = false)
data class TimeSeriesEntryDto(
    @Json(name = "1. open") val open: String,
    @Json(name = "2. high") val high: String,
    @Json(name = "3. low") val low: String,
    @Json(name = "4. close") val close: String,
    @Json(name = "5. volume") val volume: String,
)
