package com.binitkumar.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldDefaults
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.binitkumar.cryptotracker.core.presentation.util.ObserveAsEvents
import com.binitkumar.cryptotracker.core.presentation.util.toToastMessage
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.screen.CoinDetailScreen
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.screen.CoinListScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveNavigation(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) {
        when (it) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    it.error.toToastMessage(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action->
                        viewModel.onAction(action)
                        when(action){
                            is CoinListAction.OnClickCoin ->{
                                navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                            }
                        }

                    },
                    modifier = modifier,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(
                    state = state,
                    modifier = modifier
                )
            }
        },
        modifier = modifier,

    )
}