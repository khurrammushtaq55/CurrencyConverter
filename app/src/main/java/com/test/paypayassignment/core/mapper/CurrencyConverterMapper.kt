package com.test.paypayassignment.core.mapper

import com.test.paypayassignment.core.utils.convertCurrency
import com.test.paypayassignment.presentation.model.CurrencyRates

class CurrencyConverterMapper :
    ConverterMapper<List<CurrencyRates>, Double, Double, List<CurrencyRates>> {
    override fun to(t1: List<CurrencyRates>, t2: Double, t3: Double): List<CurrencyRates> {
        return t1.map { rate ->
            CurrencyRates(rate.countryIdentifier, convertCurrency(t3, t2, rate.currencyRate))
        }
    }
}