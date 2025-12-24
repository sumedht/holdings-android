package com.sumedh.demo.fake

import android.R.attr.data
import com.sumedh.demo.data.local.dao.HoldingsDao
import com.sumedh.demo.data.local.entity.HoldingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHoldingsDao : HoldingsDao {
    private val data = MutableStateFlow<List<HoldingEntity>>(emptyList())

    override fun observeHoldings(): Flow<List<HoldingEntity>> = data

    override suspend fun insertAll(items: List<HoldingEntity>) {
        data.value = items
    }
}