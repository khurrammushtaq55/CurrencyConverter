package com.test.paypayassignment.data.db

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.db.model.ExRatesDbModel
import javax.inject.Inject

class ExRateLocalDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    ExRateLocalDataSource {

    override suspend fun insertExRates(exRatesDbModel: ExRatesDbModel) {
        try {
            appDatabase.exchangeRateDao().insertExchangeRate(exRatesDbModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getLocalExRates(): Result<ExRatesDbModel> {
        return try {
            val localExRates = appDatabase.exchangeRateDao().getAllExchangeRates()
            if (localExRates.rates.isNullOrEmpty().not()) {
                Result.Success(localExRates)
            } else {
                Result.Error("Error Occurred While Fetching Data from Local Database")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error Occurred While Fetching Data from Local Database")
        }
    }
}