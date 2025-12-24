package com.sumedh.demo.data.remote

import com.sumedh.demo.data.remote.dto.HoldingsResponse
import retrofit2.http.GET

interface HoldingsApi {

    @GET(".")
    suspend fun getHoldings(): HoldingsResponse
}