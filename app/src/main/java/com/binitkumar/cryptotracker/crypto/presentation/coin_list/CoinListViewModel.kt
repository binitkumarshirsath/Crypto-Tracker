package com.binitkumar.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binitkumar.cryptotracker.core.domain.util.onError
import com.binitkumar.cryptotracker.core.domain.util.onSuccess
import com.binitkumar.cryptotracker.crypto.domain.CoinDataSource
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.DataPoint
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.model.CoinListScreenState
import com.binitkumar.cryptotracker.crypto.presentation.model.CoinUi
import com.binitkumar.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {


    private val _state = MutableStateFlow(CoinListScreenState())
    val state = _state.onStart { loadCoins() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListScreenState()
    )


    private val _events = Channel<CoinListEvent> {}
    val events = _events.receiveAsFlow()


    fun onAction(coinListAction: CoinListAction) {

        when (coinListAction) {
            is CoinListAction.OnClickCoin -> {
                selectCoin(coinUi = coinListAction.coin)
            }
        }
    }


    private fun selectCoin(coinUi: CoinUi) {
        _state.update {
            it.copy(selectedCoin = coinUi)
        }

        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUi.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.dateTime }
                    .map {
                        DataPoint(
                            x = it.dateTime.hour.toFloat(),
                            y = it.priceUsd.toFloat(),
                            xLabel = DateTimeFormatter
                                .ofPattern("ha\nM/d")
                                .format(it.dateTime)
                        )
                    }

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }
                .onError { error ->
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            coins = coins.map { coin ->
                                coin.toCoinUi()
                            },
                            isLoading = false
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(isLoading = false)
                    }

                    _events.send(
                        CoinListEvent.Error(error = it)
                    )
                }
        }
    }

}