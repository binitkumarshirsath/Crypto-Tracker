package com.binitkumar.cryptotracker.crypto.presentation.coin_list

import com.binitkumar.cryptotracker.crypto.presentation.model.CoinUi

sealed interface CoinListAction {
    data class OnClickCoin(val coin: CoinUi):CoinListAction
}