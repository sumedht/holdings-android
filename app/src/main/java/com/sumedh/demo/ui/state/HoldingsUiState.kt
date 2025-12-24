package com.sumedh.demo.ui.state

import com.sumedh.demo.domain.model.Holding
import com.sumedh.demo.domain.model.PortfolioSummary

data class HoldingsUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val holdings: List<Holding> = emptyList(),
    val summary: PortfolioSummary? = null,
    val isExpanded: Boolean = false,
    val isInternetAvailable: Boolean = true,
    val error: String? = null
)