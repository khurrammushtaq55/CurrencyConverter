package com.test.paypayassignment.core

import com.test.paypayassignment.presentation.model.CurrencyRates

sealed class ExRatesUiState {
    class Success(val rates: List<CurrencyRates>) : ExRatesUiState()
    class Failure(val errorText: String) : ExRatesUiState()
    object Loading : ExRatesUiState()
    object Empty : ExRatesUiState()
}