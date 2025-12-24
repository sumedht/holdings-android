package com.sumedh.demo.ui.viewModel

import com.sumedh.demo.data.local.entity.HoldingEntity
import com.sumedh.demo.data.repository.HoldingsRepositoryImpl
import com.sumedh.demo.domain.usecase.GetHoldingsUseCase
import com.sumedh.demo.domain.usecase.RefreshHoldingsUseCase
import com.sumedh.demo.fake.FakeConnectivityObserver
import com.sumedh.demo.fake.FakeHoldingsApi
import com.sumedh.demo.fake.FakeHoldingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    private val api = FakeHoldingsApi()
    private val dao = FakeHoldingsDao()
    private val repository = HoldingsRepositoryImpl(api, dao)
    private val useCase = GetHoldingsUseCase(repository)
    private val connectivityObserver = FakeConnectivityObserver()
    private val refreshHoldingsUseCase = RefreshHoldingsUseCase(repository)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads holdings on init`() = runTest {
        val viewModel = HoldingsViewModel(
            useCase,
            refreshHoldingsUseCase,
            connectivityObserver)
        advanceUntilIdle()

        val state = viewModel.state.value

        assertFalse(state.isLoading)
        assertEquals(1, state.holdings.size)
        assertNotNull(state.summary)
    }

    @Test
    fun `ViewModel shows cached data from db`() = runTest {
        val dao = FakeHoldingsDao()
        dao.insertAll(
            listOf(
                HoldingEntity(
                    "INFY",
                    5,
                    1500.0,
                    1200.0,
                    1480.0
                )
            )
        )

        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(IOException()), // network fails
            dao
        )

        val viewModel = HoldingsViewModel(
            GetHoldingsUseCase(repository),
            RefreshHoldingsUseCase(repository),
            FakeConnectivityObserver()
        )

        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(1, state.holdings.size)
    }

    @Test
    fun `ViewModel handles empty db gracefully`() = runTest {
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(IOException()),
            FakeHoldingsDao()
        )

        val viewModel = HoldingsViewModel(
            GetHoldingsUseCase(repository),
            RefreshHoldingsUseCase(repository),
            FakeConnectivityObserver()
        )

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.holdings.isEmpty())
    }

    @Test
    fun `ViewModel refreshFromNetwork updates holdings`() = runTest {
        val repository = HoldingsRepositoryImpl(
            FakeHoldingsApi(),
            FakeHoldingsDao()
        )

        val viewModel = HoldingsViewModel(
            GetHoldingsUseCase(repository),
            RefreshHoldingsUseCase(repository),
            FakeConnectivityObserver()
        )

        viewModel.refreshFromNetwork()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state.holdings.isNotEmpty())
    }
}
