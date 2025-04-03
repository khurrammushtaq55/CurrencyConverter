package com.test.paypayassignment.data.api.service

import com.test.paypayassignment.BuildConfig
import com.test.paypayassignment.data.api.model.ExRatesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/latest.json?app_id=${BuildConfig.API_KEY}")
    suspend fun getExRates(): Response<ExRatesApiResponse>
}