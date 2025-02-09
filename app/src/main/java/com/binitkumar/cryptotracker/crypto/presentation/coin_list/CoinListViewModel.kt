package com.binitkumar.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binitkumar.cryptotracker.core.domain.util.onError
import com.binitkumar.cryptotracker.core.domain.util.onSuccess
import com.binitkumar.cryptotracker.crypto.domain.CoinDataSource
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.model.CoinListScreenState
import com.binitkumar.cryptotracker.crypto.presentation.model.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListScreenState())
    val state = _state.onStart { loadCoins() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListScreenState()
    )

    fun onAction(coinListAction: CoinListAction){
        when(coinListAction){
            is CoinListAction.OnClickCoin -> {
                _state.update {
                    it.copy(selectedCoin = coinListAction.coin)
                }
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
                }
        }
    }

}