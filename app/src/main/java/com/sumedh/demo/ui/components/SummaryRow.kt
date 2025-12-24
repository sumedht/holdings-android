package com.sumedh.demo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SummaryRow(
    label: String,
    value: Double,
    highlight: Boolean = false
) {
    val color = when {
        !highlight -> Color.Unspecified
        value >= 0 -> Color(0xFF2E7D32)
        else -> Color(0xFFC62828)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            text = "₹ %.2f".format(value),
            color = color,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal
        )
    }
}