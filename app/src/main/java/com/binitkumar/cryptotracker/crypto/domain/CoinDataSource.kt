package com.binitkumar.cryptotracker.crypto.domain

import com.binitkumar.cryptotracker.core.domain.util.NetworkError
import com.binitkumar.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins() : Result<List<Coin>,NetworkError>
}