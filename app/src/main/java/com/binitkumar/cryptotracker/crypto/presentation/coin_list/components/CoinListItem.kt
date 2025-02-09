package com.binitkumar.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.binitkumar.cryptotracker.crypto.domain.Coin
import com.binitkumar.cryptotracker.crypto.presentation.model.CoinUi
import com.binitkumar.cryptotracker.crypto.presentation.model.toCoinUi
import com.binitkumar.cryptotracker.ui.theme.CryptoTrackerTheme
import com.binitkumar.cryptotracker.ui.theme.greenBackground

@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconResource),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(85.dp)
        )

        Column(modifier.weight(1f)) {
            Text(
                text = coinUi.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor

            )
            Text(
                text = coinUi.symbol,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$ ${coinUi.marketCapUSD.formattedValue}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = textColor

            )

            val isPositiveChange = if (coinUi.changePercentage24Hr.value < 0.0)
                false
            else
                true

            val containerColor = if (isPositiveChange) {
                greenBackground
            } else {
                MaterialTheme.colorScheme.onErrorContainer
            }

            val textColor = if (isPositiveChange) {
                Color.Green
            } else {
                MaterialTheme.colorScheme.errorContainer
            }

            val icon = if (isPositiveChange) {
                Icons.Default.ArrowUpward
            } else {
                Icons.Default.ArrowDownward
            }

            Row(
                modifier = Modifier

                    .clip(
                        RoundedCornerShape(100)
                    )
                    .background(containerColor)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = textColor
                )

                Text(
                    text = "${coinUi.changePercentage24Hr.formattedValue} %",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                    modifier = Modifier.padding(end = 10.dp)
                )


            }
        }


    }
}


@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun CoinListItemPreview() {
    CryptoTrackerTheme {
        CoinListItem(
            coinUi = previewCoin.toCoinUi(),
            onClick = {},
        )
    }
}

internal val previewCoin = Coin(
    id = "bitcoin",
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUSD = 124134242.56,
    priceUSD = 342424.45,
    changePercentage24Hr = -0.1

)