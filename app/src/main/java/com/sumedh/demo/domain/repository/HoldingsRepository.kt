package com.sumedh.demo.domain.repository

import com.sumedh.demo.common.Resource
import com.sumedh.demo.domain.model.Holding
import kotlinx.coroutines.flow.Flow

interface HoldingsRepository {
    fun getHoldings(): Flow<Resource<List<Holding>>>

    fun refreshHoldings(): Flow<Resource<List<Holding>>>
}