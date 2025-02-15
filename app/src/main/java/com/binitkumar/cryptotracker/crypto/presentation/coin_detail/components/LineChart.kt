package com.binitkumar.cryptotracker.crypto.presentation.coin_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.ChartStyle
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.DataPoint

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPoints: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true

) {

}