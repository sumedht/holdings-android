package com.sumedh.demo.ui.components

import com.sumedh.demo.ui.state.HoldingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FakeHoldingsViewModel(
    initialState: HoldingsUiState
) : HoldingsViewModelContract {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<HoldingsUiState> = _state

    override fun toggleSummary() {
        _state.update { it.copy(isExpanded = !it.isExpanded) }
    }
}

interface HoldingsViewModelContract {
    val state: StateFlow<HoldingsUiState>
    fun toggleSummary()
}