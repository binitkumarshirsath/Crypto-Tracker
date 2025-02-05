package com.plcoding.cryptotracker.crypto.presentation.coin_list.model

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.presentation.model.CoinUi


@Immutable
data class CoinListScreenState(
    val isLoading : Boolean = false,
    val coins : List<CoinUi> = emptyList(),
    val selectedCoin : CoinUi? = null
)
