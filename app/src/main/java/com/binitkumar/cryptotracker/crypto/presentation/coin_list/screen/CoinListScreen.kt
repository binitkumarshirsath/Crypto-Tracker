package com.binitkumar.cryptotracker.crypto.presentation.coin_list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.model.CoinListScreenState
import com.binitkumar.cryptotracker.crypto.presentation.model.CoinUi
import com.binitkumar.cryptotracker.crypto.presentation.model.toCoinUi
import com.binitkumar.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    state: CoinListScreenState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {


    if (state.isLoading) {
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { item: CoinUi ->
                CoinListItem(
                    coinUi = item,
                    onClick = { onAction(CoinListAction.OnClickCoin(coin = item)) }
                )

                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CoinListScreenPrev() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListScreenState(
                coins = (0..100).map {
                    previewCoin.toCoinUi().copy(id = it.toString())
                }
            ),
            onAction = {}
        )
    }
}