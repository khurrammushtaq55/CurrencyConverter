package com.test.paypayassignment.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.paypayassignment.core.mapper.CurrencyConverterMapper
import com.test.paypayassignment.core.ExRatesUiState
import com.test.paypayassignment.core.Result
import com.test.paypayassignment.core.utils.isDataStale
import com.test.paypayassignment.domain.usecase.ExchangeRatesUseCase
import com.test.paypayassignment.presentation.model.ExRatesUiModel
import com.test.paypayassignment.presentation.model.CurrencyRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ExRateViewModel @Inject constructor(
    private val exchangeRatesUseCase: ExchangeRatesUseCase,
    private val convertMapper: CurrencyConverterMapper,
) :
    ViewModel() {

    private var convertJob: Job? = null
    var lastFetchedRatesTimeStamp = 0L
    val lastFetchedCurrencies = mutableListOf<String>()
    val lastFetchedRates = mutableListOf<CurrencyRates>()

    private val _conversion = MutableStateFlow<ExRatesUiState>(ExRatesUiState.Empty)
    val conversion: StateFlow<ExRatesUiState> get() = _conversion

    var lastSelectedCurrency: String = "USD"

    init {
        resetCalculations()
    }

    fun onChange(amount: String) {
        _conversion.value = ExRatesUiState.Loading
        if (isLocalDataValid()) {
            fetchInstantData(amount)
        } else {
            updateInstantData(amount)
        }
    }

    private fun isLocalDataValid(): Boolean {
        return lastFetchedRatesTimeStamp != 0L &&
                lastFetchedRates.isNotEmpty() &&
                !isDataStale(System.currentTimeMillis(), lastFetchedRatesTimeStamp)
    }

    @VisibleForTesting
    fun fetchInstantData(amount: String) {
        convertJob?.cancel()
        convertJob = viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            val fromCurrencyRate = lastFetchedRates.find {
                it.countryIdentifier.lowercase(Locale.getDefault()) == lastSelectedCurrency.lowercase(Locale.getDefault())
            }?.currencyRate ?: return@launch

            val convertedRates = convertMapper.to(lastFetchedRates, fromCurrencyRate, amount.toDoubleOrNull() ?: 1.0)
            _conversion.value = ExRatesUiState.Success(convertedRates)

        }
    }
    @VisibleForTesting
    fun updateInstantData(amount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val ratesResponse = exchangeRatesUseCase.getExchangeRates()
            when (ratesResponse) {

                is Result.Success -> handleSuccessResponse(ratesResponse.data, amount)
                is Result.Error -> _conversion.value = ExRatesUiState.Failure(ratesResponse.message.orEmpty())
            }
        }
    }

    @VisibleForTesting
    fun handleSuccessResponse(currency: ExRatesUiModel?, amount: String) {
        if (currency?.rates != null) {
            lastFetchedRates.clear()
            lastFetchedCurrencies.clear()
            lastFetchedRates.addAll(currency.rates)
            lastFetchedCurrencies.addAll(currency.rates.map { it.countryIdentifier })
            lastFetchedRatesTimeStamp = currency.lastFetchTimeStamp
            onChange(amount)
        }
    }

    fun resetCalculations() {
        onChange("1")
    }
}