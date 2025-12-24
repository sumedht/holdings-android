package com.sumedh.demo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumedh.demo.common.Resource
import com.sumedh.demo.data.network.ConnectivityObserver
import com.sumedh.demo.domain.model.calculateSummary
import com.sumedh.demo.domain.usecase.GetHoldingsUseCase
import com.sumedh.demo.domain.usecase.RefreshHoldingsUseCase
import com.sumedh.demo.ui.state.HoldingsUiState
import com.sumedh.demo.ui.toUiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldingsUseCase: GetHoldingsUseCase,
    private val refreshHoldingsUseCase: RefreshHoldingsUseCase,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {

    private val _state = MutableStateFlow(HoldingsUiState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        observeConnectivity()
        loadHoldings()
        refreshFromNetwork()
    }

    private fun loadHoldings() {

        viewModelScope.launch {
            getHoldingsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                //isRefreshing = !showLoader
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                holdings = result.data ?: emptyList(),
                                summary = calculateSummary(result.data ?: emptyList()),
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.error?.toUiMessage()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { isConnected ->
                _state.update {
                    it.copy(isInternetAvailable = isConnected)
                }
            }
        }
    }

    fun refreshFromNetwork() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            refreshHoldingsUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                holdings = result.data ?: emptyList(),
                                summary = calculateSummary(result.data ?: emptyList()),
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.error?.toUiMessage()
                            )
                        }
                    }
                }
            }
        }
    }

    fun toggleSummary() {
        _state.update { it.copy(isExpanded = !it.isExpanded) }
    }
}