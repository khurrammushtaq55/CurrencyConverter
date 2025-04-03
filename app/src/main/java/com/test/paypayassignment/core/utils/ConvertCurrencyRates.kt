package com.test.paypayassignment.core.utils

fun convertCurrency(
    amount: Double,
    fromCurrencyRate: Double,
    toCurrencyRate: Double
): Double {
    val fromCurrencyUsdEqual = 1.0 / fromCurrencyRate
    return roundOffDecimal((fromCurrencyUsdEqual * toCurrencyRate) * amount)
}