package com.aureum.ticker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class QuoteResponseDto(
    @Json(name = "Global Quote") val quote: GlobalQuoteDto?,
)

@JsonClass(generateAdapter = false)
data class GlobalQuoteDto(
    @Json(name = "01. symbol") val symbol: String,
    @Json(name = "02. open") val open: String,
    @Json(name = "03. high") val high: String,
    @Json(name = "04. low") val low: String,
    @Json(name = "05. price") val price: String,
    @Json(name = "06. volume") val volume: String,
    @Json(name = "08. previous close") val previousClose: String,
    @Json(name = "09. change") val change: String,
    @Json(name = "10. change percent") val changePercent: String,
)
