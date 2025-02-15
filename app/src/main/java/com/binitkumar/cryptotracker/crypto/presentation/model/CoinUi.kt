package com.binitkumar.cryptotracker.crypto.presentation.model

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.binitkumar.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.binitkumar.cryptotracker.crypto.domain.Coin
import com.binitkumar.cryptotracker.crypto.presentation.coin_detail.DataPoint
import java.util.Locale


//This is what we show on screen, wo basically formatting the data
data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUSD: FormattedNumber,
    val priceUSD: FormattedNumber,
    val changePercentage24Hr: FormattedNumber,
    @DrawableRes val iconResource: Int,
    val coinPriceHistory: List<DataPoint> = emptyList()
)


data class FormattedNumber(
    val value: Double,
    val formattedValue: String
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = this.id,
        rank = this.rank,
        name = this.name,
        symbol = this.symbol,
        marketCapUSD = this.marketCapUSD.toDisplayNumber(),
        priceUSD = this.priceUSD.toDisplayNumber(),
        changePercentage24Hr = this.changePercentage24Hr.toDisplayNumber(),
        iconResource = getDrawableIdForCoin(this.symbol),
    )
}

fun Double.toDisplayNumber(): FormattedNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return FormattedNumber(
        value = this,
        formattedValue = formatter.format(this)
    )
}