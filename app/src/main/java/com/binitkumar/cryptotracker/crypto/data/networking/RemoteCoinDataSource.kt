package com.binitkumar.cryptotracker.crypto.data.networking

import com.binitkumar.cryptotracker.core.data.networking.constructUrl
import com.binitkumar.cryptotracker.core.data.networking.safeCall
import com.binitkumar.cryptotracker.core.domain.util.NetworkError
import com.binitkumar.cryptotracker.core.domain.util.Result
import com.binitkumar.cryptotracker.core.domain.util.map
import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.binitkumar.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.binitkumar.cryptotracker.crypto.domain.Coin
import com.binitkumar.cryptotracker.crypto.domain.CoinDataSource
import com.binitkumar.cryptotracker.crypto.domain.CoinPrice
import com.binitkumar.cryptotracker.crypto.mappers.toCoin
import com.binitkumar.cryptotracker.crypto.mappers.toCoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {

        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()


        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history"),
            ) {
                parameter(key = "interval", value = "h6")
                parameter(key = "start", value = startMillis)
                parameter(key = "end", value = endMillis)
            }
        }.map { response ->
            response.data.map {
                it.toCoinPrice()
            }
        }
    }
}