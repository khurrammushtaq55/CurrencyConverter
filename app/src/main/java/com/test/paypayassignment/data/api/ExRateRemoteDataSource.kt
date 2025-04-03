package com.test.paypayassignment.data.api

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.api.model.ExRatesApiResponse

interface ExRateRemoteDataSource {
    suspend fun getExRates(): Result<ExRatesApiResponse>
}