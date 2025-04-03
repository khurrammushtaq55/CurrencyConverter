package com.test.paypayassignment.data.db

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.db.model.ExRatesDbModel

interface ExRateLocalDataSource {

    suspend fun insertExRates(exRatesDbModel: ExRatesDbModel)
    suspend fun getLocalExRates(): Result<ExRatesDbModel>
}