package com.binitkumar.cryptotracker.crypto.mappers

import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinDto
import com.binitkumar.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin():Coin{
    return Coin(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name,
        marketCapUSD = marketCapUsd,
        priceUSD = priceUsd,
        changePercentage24Hr = changePercent24Hr
    )
}