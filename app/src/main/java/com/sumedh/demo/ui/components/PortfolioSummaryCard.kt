package com.sumedh.demo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sumedh.demo.domain.model.PortfolioSummary

@Composable
fun PortfolioSummaryCard(
    summary: PortfolioSummary,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    val pnlColor =
        if (summary.totalPnl >= 0) Color(0xFF2E7D32)
        else Color(0xFFC62828)

    val pnlPercentage =
        if (summary.totalInvestment == 0.0) 0.0
        else (summary.totalPnl / summary.totalInvestment) * 100

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("summary_card")
            .clickable { onToggle() },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("summary_expanded")
                        .padding(bottom = 16.dp)
                ) {
                    SummaryRow("Current Value", summary.currentValue)
                    SummaryRow("Total Investment", summary.totalInvestment)
                    SummaryRow(
                        "Today's P&L",
                        summary.todaysPnl,
                        highlight = true
                    )
                    Divider()
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profit & Loss",
                    style = MaterialTheme.typography.titleMedium
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowDown
                    else
                        Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₹ %.2f".format(summary.totalPnl),
                        color = pnlColor,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "(%.2f%%)".format(pnlPercentage),
                        color = pnlColor,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }
}