package com.binitkumar.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.binitkumar.cryptotracker.core.presentation.util.ObserveAsEvents
import com.binitkumar.cryptotracker.core.presentation.util.toToastMessage
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.binitkumar.cryptotracker.crypto.presentation.coin_list.screen.CoinListScreen
import com.binitkumar.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                Scaffold { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
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

                    CoinListScreen(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

