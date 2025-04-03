package com.test.paypayassignment.core.mapper

import com.google.gson.Gson
import com.test.paypayassignment.presentation.model.CurrencyRates
import com.test.paypayassignment.presentation.model.ExRatesUiModel

class CurrencyLocalMapper() : Mapper<String?, ExRatesUiModel> {
    override fun to(t: String?): ExRatesUiModel {
        val currency = Gson().fromJson(t, ExRatesUiModel::class.java)
        val currencyRates = currency.rates.map {
            CurrencyRates(it.countryIdentifier, it.currencyRate)
        }
        return ExRatesUiModel(currency.lastFetchTimeStamp, currencyRates)
    }
}