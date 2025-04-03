package com.test.paypayassignment.data.api

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.api.model.ExRatesApiResponse
import com.test.paypayassignment.data.api.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExRateRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    ExRateRemoteDataSource {

    override suspend fun getExRates(): Result<ExRatesApiResponse> {
        return try {

            withContext(Dispatchers.IO) {
                val apiResponse = apiService.getExRates()
                if (apiResponse.isSuccessful && apiResponse.body() != null) {
                    Result.Success(apiResponse.body()!!)
                } else {
                    Result.Error("Error Occurred While Fetching Data from Server")
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error Occurred While Fetching Data from Server")
        }
    }
}