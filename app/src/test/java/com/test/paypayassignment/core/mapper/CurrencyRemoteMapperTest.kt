package com.test.paypayassignment.core.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyRemoteMapperTest {

    private val mapper = CurrencyRemoteMapper()

    @Test
    fun `to should correctly map a valid map to ExRatesUiModel`() {
        // Given
        val inputMap = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "JPY" to 110.0
        )

        // When
        val result = mapper.to(inputMap)

        // Then
        assertTrue(result.lastFetchTimeStamp <= System.currentTimeMillis())
        assertEquals(3, result.rates.size)

        val usdRate = result.rates.find { it.countryIdentifier == "USD" }
        val eurRate = result.rates.find { it.countryIdentifier == "EUR" }
        val jpyRate = result.rates.find { it.countryIdentifier == "JPY" }

        assertEquals(1.0, usdRate?.currencyRate!!, 0.001)
        assertEquals(0.85, eurRate?.currencyRate!!, 0.001)
        assertEquals(110.0, jpyRate?.currencyRate!!, 0.001)
    }

    @Test
    fun `to should return ExRatesUiModel with empty list when input map is null`() {
        // Given
        val inputMap: Map<String, Double>? = null

        // When
        val result = mapper.to(inputMap)

        // Then
        assertTrue(result.lastFetchTimeStamp <= System.currentTimeMillis())
        assertTrue(result.rates.isEmpty())
    }


    @Test
    fun `to should round off decimal values`() {
        // Given
        val inputMap = mapOf(
            "USD" to 1.00000000,
            "EUR" to 0.99999,
            "JPY" to 110.9999999
        )
        // When
        val result = mapper.to(inputMap)

        // Then
        assertTrue(result.lastFetchTimeStamp <= System.currentTimeMillis())
        assertEquals(3, result.rates.size)

        val usdRate = result.rates.find { it.countryIdentifier == "USD" }
        val eurRate = result.rates.find { it.countryIdentifier == "EUR" }
        val jpyRate = result.rates.find { it.countryIdentifier == "JPY" }

        assertEquals(1.0, usdRate?.currencyRate!!, 0.001)
        assertEquals(1.0, eurRate?.currencyRate!!, 0.001)
        assertEquals(111.0, jpyRate?.currencyRate!!, 0.001)
    }

    @Test
    fun `to should handle map with negative values`() {
        // Given
        val inputMap = mapOf(
            "USD" to -1.0,
            "EUR" to -0.85,
            "JPY" to -110.0
        )

        // When
        val result = mapper.to(inputMap)

        // Then
        assertTrue(result.lastFetchTimeStamp <= System.currentTimeMillis())
        assertEquals(3, result.rates.size)

        val usdRate = result.rates.find { it.countryIdentifier == "USD" }
        val eurRate = result.rates.find { it.countryIdentifier == "EUR" }
        val jpyRate = result.rates.find { it.countryIdentifier == "JPY" }

        assertEquals(-1.0, usdRate?.currencyRate!!, 0.001)
        assertEquals(-0.85, eurRate?.currencyRate!!, 0.001)
        assertEquals(-110.0, jpyRate?.currencyRate!!, 0.001)
    }
}