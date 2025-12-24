package com.sumedh.demo.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sumedh.demo.ui.viewModel.HoldingsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HoldingsScreen(
    viewModel: HoldingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HoldingsContent(
        state = state,
        onToggleSummary = viewModel::toggleSummary,
        onRefresh = viewModel::refreshFromNetwork
    )
}