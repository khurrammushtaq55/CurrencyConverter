package com.test.paypayassignment.presentation.model

data class ExRatesUiModel (
    val lastFetchTimeStamp: Long,
    val rates: List<CurrencyRates>
)

data class CurrencyRates(
    val countryIdentifier: String,
    val currencyRate: Double,
)