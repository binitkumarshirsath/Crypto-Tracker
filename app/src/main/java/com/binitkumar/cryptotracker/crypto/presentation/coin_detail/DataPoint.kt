package com.binitkumar.cryptotracker.crypto.presentation.coin_detail

import java.text.NumberFormat
import java.util.Locale

data class DataPoint(
    val x: Float,
    val y: Float,
    val xLabel: String,
    val yLabel: ValueLabel
)

data class ValueLabel(
    val value: Float,
    val unit: String
) {
    fun formatted(): String {
        val formater = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
                value > 1000 -> 0
                value in 2f..999f -> 2
                else -> 3
            }

            maximumFractionDigits = fractionDigits
            minimumFractionDigits = 0
        }

        return "${formater.format(value)} $unit"
    }
}
