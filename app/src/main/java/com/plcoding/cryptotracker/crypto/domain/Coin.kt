package com.plcoding.cryptotracker.crypto.domain


//what we get from api
data class Coin(
    val id : String,
    val rank : Int,
    val name : String,
    val symbol : String,
    val marketCapUSD: Double,
    val priceUSD : Double,
    val changePercentage24Hr : Double
)