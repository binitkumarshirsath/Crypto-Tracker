package com.binitkumar.cryptotracker.crypto.mappers

import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinDto
import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.binitkumar.cryptotracker.crypto.domain.Coin
import com.binitkumar.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

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

fun CoinPriceDto.toCoinPrice():CoinPrice{
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC"))
    )
}