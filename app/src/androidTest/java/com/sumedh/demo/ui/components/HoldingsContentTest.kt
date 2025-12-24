package com.sumedh.demo.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sumedh.demo.domain.model.Holding
import com.sumedh.demo.domain.model.calculateSummary
import com.sumedh.demo.ui.state.HoldingsUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HoldingsContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleHoldings = listOf(
        Holding(
            symbol = "INFY",
            quantity = 5,
            ltp = 1500.0,
            avgPrice = 1200.0,
            close = 1480.0
        )
    )

    private fun launch(
        state: HoldingsUiState,
        onRefresh: () -> Unit = {},
        onToggle: () -> Unit = {}
    ) {
        composeRule.setContent {
            HoldingsContent(
                state = state,
                onToggleSummary = onToggle,
                onRefresh = onRefresh
            )
        }
    }

    @Test
    fun top_bar_is_displayed() {
        launch(
            HoldingsUiState(
                holdings = sampleHoldings,
                summary = calculateSummary(sampleHoldings),
                isLoading = false
            )
        )

        composeRule
            .onNodeWithTag("top_bar")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("Holdings")
            .assertIsDisplayed()
    }

    // ---------------------------
    // HOLDINGS LIST
    // ---------------------------

    @Test
    fun holdings_list_is_displayed() {
        launch(
            HoldingsUiState(
                holdings = sampleHoldings,
                summary = calculateSummary(sampleHoldings),
                isLoading = false
            )
        )

        composeRule
            .onNodeWithTag("holding_row_INFY")
            .assertIsDisplayed()
    }

    // ---------------------------
    // INTERNET BANNER
    // ---------------------------

    @Test
    fun no_internet_banner_is_shown_when_offline() {
        launch(
            HoldingsUiState(
                isInternetAvailable = false,
                isLoading = false
            )
        )

        composeRule
            .onNodeWithTag("no_internet_banner")
            .assertIsDisplayed()
    }

    // ---------------------------
    // SUMMARY EXPAND / COLLAPSE
    // ---------------------------

    @Test
    fun summary_expands_and_collapses() {

        composeRule.setContent {

            var expanded by remember {
                mutableStateOf(false)
            }

            HoldingsContent(
                state = HoldingsUiState(
                    holdings = sampleHoldings,
                    summary = calculateSummary(sampleHoldings),
                    isExpanded = expanded,
                    isLoading = false
                ),
                onToggleSummary = {
                    expanded = !expanded
                },
                onRefresh = {}
            )
        }

        // Expand
        composeRule
            .onNodeWithTag("summary_card")
            .assertIsDisplayed()
            .performClick()

        composeRule
            .onNodeWithTag("summary_card")
            .performClick()

        composeRule
            .onNodeWithTag("summary_expanded")
            .assertDoesNotExist()
    }
}
