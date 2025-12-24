package com.sumedh.demo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sumedh.demo.domain.model.Holding


@Entity(tableName = "holdings")
data class HoldingEntity(
    @PrimaryKey val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)

fun HoldingEntity.toHolding(): Holding {
    return Holding(
        symbol = symbol,
        quantity = quantity,
        ltp = ltp,
        avgPrice = avgPrice,
        close = close
    )
}

