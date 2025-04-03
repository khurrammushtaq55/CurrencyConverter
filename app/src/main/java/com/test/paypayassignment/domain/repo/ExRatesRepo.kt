package com.test.paypayassignment.domain.repo

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.db.model.ExRatesDbModel
import com.test.paypayassignment.data.api.model.ExRatesApiResponse
import com.test.paypayassignment.presentation.model.ExRatesUiModel

interface ExRatesRepo {

    suspend fun getExchangeRates(): Result<ExRatesUiModel>

}