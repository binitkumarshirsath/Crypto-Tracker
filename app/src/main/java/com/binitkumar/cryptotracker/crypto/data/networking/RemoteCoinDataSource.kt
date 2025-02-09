package com.binitkumar.cryptotracker.crypto.data.networking

import com.binitkumar.cryptotracker.core.data.networking.constructUrl
import com.binitkumar.cryptotracker.core.data.networking.safeCall
import com.binitkumar.cryptotracker.core.domain.util.NetworkError
import com.binitkumar.cryptotracker.core.domain.util.Result
import com.binitkumar.cryptotracker.core.domain.util.map
import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.binitkumar.cryptotracker.crypto.domain.Coin
import com.binitkumar.cryptotracker.crypto.domain.CoinDataSource
import com.binitkumar.cryptotracker.crypto.mappers.toCoin
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl(url = "/assets")
            )
        }.map { response ->
            response.data.map { coinDto ->
                coinDto.toCoin()
            }
        }
    }
}