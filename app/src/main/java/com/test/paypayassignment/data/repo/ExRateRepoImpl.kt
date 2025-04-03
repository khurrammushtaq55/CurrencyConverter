package com.test.paypayassignment.data.repo

import com.google.gson.Gson
import com.test.paypayassignment.core.mapper.CurrencyLocalMapper
import com.test.paypayassignment.core.mapper.CurrencyRemoteMapper
import com.test.paypayassignment.core.Result
import com.test.paypayassignment.core.utils.isDataStale
import com.test.paypayassignment.data.api.ExRateRemoteDataSource
import com.test.paypayassignment.data.db.ExRateLocalDataSource
import com.test.paypayassignment.data.db.model.ExRatesDbModel
import com.test.paypayassignment.data.api.model.ExRatesApiResponse
import com.test.paypayassignment.domain.repo.ExRatesRepo
import com.test.paypayassignment.presentation.model.ExRatesUiModel
import javax.inject.Inject

class ExRateRepoImpl @Inject constructor(
    private val exRateLocalDataSource: ExRateLocalDataSource,
    private val exRateRemoteDataSource: ExRateRemoteDataSource,
    private val localRatesMapper: CurrencyLocalMapper,
    private val remoteRatesMapper: CurrencyRemoteMapper
) : ExRatesRepo
{

    override suspend fun getExchangeRates(): Result<ExRatesUiModel> {
        val isLocalDataStaled = isDataStale(
            System.currentTimeMillis(), getLocalExRates().data?.timeStamp ?: 0
        )
        return if (isLocalDataStaled) {
            when (val ratesResponse = getRemoteExRates()) {
                is Result.Error -> Result.Error(ratesResponse.message ?: "")
                is Result.Success -> {
                    val currency = remoteRatesMapper.to(ratesResponse.data?.rates)
                    insertExRates(ExRatesDbModel(1, Gson().toJson(currency)))
                    Result.Success(currency)
                }
            }
        } else {
            when (val ratesResponse = getLocalExRates()) {
                is Result.Error -> Result.Error(ratesResponse.message ?: "")
                is Result.Success -> {
                    val currency = localRatesMapper.to(ratesResponse.data?.rates)
                    Result.Success(currency)
                }
            }
        }
    }

    suspend fun getRemoteExRates(): Result<ExRatesApiResponse> {
        return exRateRemoteDataSource.getExRates()
    }

    suspend fun insertExRates(rates: ExRatesDbModel) {
        exRateLocalDataSource.insertExRates(rates)
    }

    suspend fun getLocalExRates(): Result<ExRatesDbModel> {
        return exRateLocalDataSource.getLocalExRates()
    }

}