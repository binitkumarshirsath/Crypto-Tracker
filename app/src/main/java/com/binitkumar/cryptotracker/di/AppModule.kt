package com.binitkumar.cryptotracker.di

import com.binitkumar.cryptotracker.core.data.networking.HttpClientFactory
import com.binitkumar.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.binitkumar.cryptotracker.crypto.domain.CoinDataSource
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf

import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClientFactory.create(
            engine = CIO.create()
        )
    }

    /*
        Whenever we ask for CoinDataSource we will get RemoteCoinDataSource
     */
    single {
        RemoteCoinDataSource(get())
    }.bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)

}