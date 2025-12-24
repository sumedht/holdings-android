package com.sumedh.demo.fake

import com.sumedh.demo.data.remote.HoldingsApi
import com.sumedh.demo.data.remote.dto.HoldingsData
import com.sumedh.demo.data.remote.dto.HoldingsDto
import com.sumedh.demo.data.remote.dto.HoldingsResponse

class FakeHoldingsApi(
    private val shouldThrow: Throwable? = null
) : HoldingsApi {

    override suspend fun getHoldings(): HoldingsResponse {
        shouldThrow?.let { throw it }

        return HoldingsResponse(
            data = HoldingsData(
                userHolding = listOf(
                    HoldingsDto(
                        symbol = "INFY",
                        quantity = 10,
                        ltp = 1500.0,
                        avgPrice = 1200.0,
                        close = 1480.0
                    )
                )
            )
        )
    }
}