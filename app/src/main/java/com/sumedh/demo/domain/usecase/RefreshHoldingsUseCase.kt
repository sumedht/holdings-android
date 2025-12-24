package com.sumedh.demo.domain.usecase

import com.sumedh.demo.domain.repository.HoldingsRepository
import javax.inject.Inject

class RefreshHoldingsUseCase @Inject constructor(
    private val repository: HoldingsRepository
) {
    suspend operator fun invoke() =
        repository.refreshHoldings()
}