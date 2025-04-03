package com.test.paypayassignment.data.api.model

data class ExRatesApiResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long?,
    val base: String?,
    val rates: HashMap<String, Double>?,
)