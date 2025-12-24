package com.sumedh.demo.data.remote.dto

import com.sumedh.demo.data.local.entity.HoldingEntity
import com.sumedh.demo.domain.model.Holding

data class HoldingsDto (
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)

fun HoldingsDto.toHolding(): Holding {
    return Holding(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}

fun HoldingsDto.toEntity(): HoldingEntity {
    return HoldingEntity(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}