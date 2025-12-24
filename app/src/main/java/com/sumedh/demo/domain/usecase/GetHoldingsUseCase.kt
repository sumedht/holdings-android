package com.sumedh.demo.domain.usecase

import com.sumedh.demo.common.Resource
import com.sumedh.demo.domain.model.Holding
import com.sumedh.demo.domain.repository.HoldingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHoldingsUseCase @Inject constructor(
    private val repository: HoldingsRepository
) {
    operator fun invoke(): Flow<Resource<List<Holding>>> {
        return repository.getHoldings()
    }
}