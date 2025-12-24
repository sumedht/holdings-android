package com.sumedh.demo.data.repository

import app.cash.turbine.test
import com.sumedh.demo.common.Resource
import com.sumedh.demo.data.local.entity.HoldingEntity
import com.sumedh.demo.domain.error.AppError
import com.sumedh.demo.fake.FakeHoldingsApi
import com.sumedh.demo.fake.FakeHoldingsDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsRepositoryImplTest {

    @Test
    fun `getHoldings emits EmptyCache when db is empty`() = runTest {
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            FakeHoldingsDao()
        )

        repository.getHoldings().test {
            val result = awaitItem()
            assert(result is Resource.Error)
            assert(result.error is AppError.EmptyCache)
            cancel()
        }
    }

    @Test
    fun `getHoldings emits Success when db has data`() = runTest {
        val dao = FakeHoldingsDao()
        dao.insertAll(
            listOf(
                HoldingEntity("TCS", 10, 3500.0, 3000.0, 3450.0)
            )
        )

        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            dao
        )

        repository.getHoldings().test {
            val result = awaitItem()
            assert(result is Resource.Success)
            assertEquals(1, result.data?.size)
            cancel()
        }
    }

    @Test
    fun `refreshHoldings updates db and emits success`() = runTest {
        val dao = FakeHoldingsDao()
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            dao
        )

        repository.refreshHoldings().test {
            val result = awaitItem()
            assert(result is Resource.Success)
            assertEquals(1, result.data?.size)

            awaitComplete()
        }
    }

    @Test
    fun `refreshHoldings emits NetworkUnavailable on IOException`() = runTest {
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(IOException()),
            FakeHoldingsDao()
        )

        repository.refreshHoldings().test {
            val result = awaitItem()
            assert(result is Resource.Error)
            assert(result.error is AppError.NetworkUnavailable)

            awaitComplete()
        }
    }
}