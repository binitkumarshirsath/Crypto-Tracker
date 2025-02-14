package com.binitkumar.cryptotracker.crypto.presentation.coin_list

import com.binitkumar.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error:NetworkError):CoinListEvent
}