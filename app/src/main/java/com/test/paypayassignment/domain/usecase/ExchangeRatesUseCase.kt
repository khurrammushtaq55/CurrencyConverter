package com.test.paypayassignment.domain.usecase

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.domain.repo.ExRatesRepo
import com.test.paypayassignment.presentation.model.ExRatesUiModel
import javax.inject.Inject


class ExchangeRatesUseCase @Inject constructor(private val repository: ExRatesRepo) {
    suspend fun getExchangeRates(): Result<ExRatesUiModel> {
        return repository.getExchangeRates()
    }
}