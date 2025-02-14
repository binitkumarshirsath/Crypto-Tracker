package com.binitkumar.cryptotracker.crypto.presentation.coin_detail.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binitkumar.cryptotracker.R
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.model.CoinListScreenState
import com.binitkumar.cryptotracker.crypto.presentation.model.toCoinUi
import com.binitkumar.cryptotracker.crypto.presentation.model.toDisplayNumber
import com.binitkumar.cryptotracker.ui.theme.CryptoTrackerTheme
import com.binitkumar.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListScreenState,
    modifier: Modifier = Modifier
) {

    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(
                    id = coin.iconResource
                ),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            FlowRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(
                        id = R.string.market_cap
                    ),
                    formattedText = "$ ${coin.marketCapUSD.formattedValue}",
                    icon = ImageVector.vectorResource(R.drawable.stock),
                )

                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUSD.formattedValue}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )
                val absoluteChangeFormatted =
                    (coin.priceUSD.value * (coin.changePercentage24Hr.value / 100))
                        .toDisplayNumber()
                val isPositive = coin.changePercentage24Hr.value > 0.0
                val contentColor = if(isPositive) {
                    if(isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = absoluteChangeFormatted.formattedValue,
                    icon = if(isPositive) {
                        ImageVector.vectorResource(id = R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
        }
    }

}


@Composable
@PreviewLightDark
fun CounDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListScreenState(
                selectedCoin = previewCoin.toCoinUi()
            )
        )

    }
}