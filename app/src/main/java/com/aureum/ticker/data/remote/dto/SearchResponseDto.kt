package com.aureum.ticker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class SearchResponseDto(
    @Json(name = "bestMatches") val matches: List<SearchMatchDto>?,
)

@JsonClass(generateAdapter = false)
data class SearchMatchDto(
    @Json(name = "1. symbol") val symbol: String,
    @Json(name = "2. name") val name: String,
    @Json(name = "3. type") val type: String,
    @Json(name = "4. region") val region: String,
)
