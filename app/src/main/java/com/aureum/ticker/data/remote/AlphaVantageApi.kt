package com.aureum.ticker.data.remote

import com.aureum.ticker.data.remote.dto.QuoteResponseDto
import com.aureum.ticker.data.remote.dto.SearchResponseDto
import com.aureum.ticker.data.remote.dto.TimeSeriesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {

    @GET("query")
    suspend fun searchTickers(
        @Query("function") function: String = "SYMBOL_SEARCH",
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String,
    ): SearchResponseDto

    @GET("query")
    suspend fun getQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String,
    ): QuoteResponseDto

    @GET("query")
    suspend fun getTimeSeries(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5min",
        @Query("apikey") apiKey: String,
    ): TimeSeriesResponseDto
}
