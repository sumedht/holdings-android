package com.sumedh.demo.domain.usecase

import app.cash.turbine.test
import com.sumedh.demo.common.Resource
import com.sumedh.demo.data.repository.HoldingsRepositoryImpl
import com.sumedh.demo.fake.FakeHoldingsApi
import com.sumedh.demo.fake.FakeHoldingsDao
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RefreshHoldingsUseCaseTest {
    @Test
    fun `RefreshHoldingsUseCase triggers network refresh`() = runTest {
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            FakeHoldingsDao()
        )

        RefreshHoldingsUseCase(repository).invoke().test {
            assert(awaitItem() is Resource.Success)
            awaitComplete()
        }
    }

}