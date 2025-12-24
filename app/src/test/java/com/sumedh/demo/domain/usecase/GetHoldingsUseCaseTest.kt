package com.sumedh.demo.domain.usecase

import app.cash.turbine.test
import com.sumedh.demo.common.Resource
import com.sumedh.demo.data.repository.HoldingsRepositoryImpl
import com.sumedh.demo.fake.FakeHoldingsApi
import com.sumedh.demo.fake.FakeHoldingsDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetHoldingsUseCaseTest {

    @Test
    fun `GetHoldingsUseCase delegates to repository`() = runTest {
        val dao = FakeHoldingsDao()
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            dao
        )

        GetHoldingsUseCase(repository).invoke().test {
            assert(awaitItem() is Resource.Error) // EmptyCache
            cancel()
        }
    }
}