package com.sumedh.demo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sumedh.demo.domain.model.Holding

@Composable
fun HoldingRow(holding: Holding) {

//    val totalInvestment = holding.avgPrice * holding.quantity
//    val currentValue = holding.ltp * holding.quantity
//    val pnl = (currentValue - totalInvestment)

    val pnl = remember(holding) {
        val totalInvestment = holding.avgPrice * holding.quantity
        val currentValue = holding.ltp * holding.quantity
        currentValue - totalInvestment
    }

    val pnlColor = if (pnl >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .testTag("holding_row_${holding.symbol}")
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = holding.symbol,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "LTP: ₹${holding.ltp}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Qty: ${holding.quantity}")

            Text(
                text = "P&L: ₹%.2f".format(pnl),
                color = pnlColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}