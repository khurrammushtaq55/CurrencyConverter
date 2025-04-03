package com.test.paypayassignment.core.mapper

import com.test.paypayassignment.core.utils.roundOffDecimal
import com.test.paypayassignment.presentation.model.CurrencyRates
import com.test.paypayassignment.presentation.model.ExRatesUiModel

class CurrencyRemoteMapper : Mapper<Map<String, Double>?, ExRatesUiModel> {
    override fun to(t: Map<String, Double>?): ExRatesUiModel {
        val currencyRatesList = t?.map { (key, value) ->
            CurrencyRates(key, roundOffDecimal(value))
        } ?: emptyList()
        return ExRatesUiModel(System.currentTimeMillis(), currencyRatesList)
    }
}