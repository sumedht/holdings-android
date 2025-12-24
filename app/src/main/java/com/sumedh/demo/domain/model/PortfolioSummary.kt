package com.sumedh.demo.domain.model

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPnl: Double,
    val todaysPnl: Double
)

fun calculateSummary(holdings: List<Holding>): PortfolioSummary {
    val currentValue = holdings.sumOf { it.ltp * it.quantity }
    val investment = holdings.sumOf { it.avgPrice * it.quantity }
    val todayPnl = holdings.sumOf { (it.close - it.ltp) * it.quantity }

    return PortfolioSummary(
        currentValue,
        investment,
        currentValue - investment,
        todayPnl
    )
}
