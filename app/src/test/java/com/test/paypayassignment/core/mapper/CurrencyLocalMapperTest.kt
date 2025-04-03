package com.test.paypayassignment.core.mapper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CurrencyLocalMapperTest {

    private val mapper = CurrencyLocalMapper()

    @Test
    fun `to should correctly map valid JSON string to ExRatesUiModel`() {
        // Given
        val jsonString = """
            {
                "lastFetchTimeStamp": 1678886400000,
                "rates": [
                    {"countryIdentifier": "USD", "currencyRate": 1.0},
                    {"countryIdentifier": "EUR", "currencyRate": 0.85},
                    {"countryIdentifier": "JPY", "currencyRate": 110.0}
                ]
            }
        """.trimIndent()

        // When
        val result = mapper.to(jsonString)

        // Then
        assertEquals(1678886400000, result.lastFetchTimeStamp)
        assertEquals(3, result.rates.size)
        assertEquals("USD", result.rates[0].countryIdentifier)
        assertEquals(1.0, result.rates[0].currencyRate, 0.001)
        assertEquals("EUR", result.rates[1].countryIdentifier)
        assertEquals(0.85, result.rates[1].currencyRate, 0.001)
        assertEquals("JPY", result.rates[2].countryIdentifier)
        assertEquals(110.0, result.rates[2].currencyRate, 0.001)
    }

    @Test
    fun `to should handle null JSON string`() {
        // Given
        val jsonString: String? = null

        // When
        val exception = assertThrows(Exception::class.java) {
            mapper.to(jsonString)
        }
        //Then
        assertEquals(NullPointerException::class.java,exception::class.java)
    }

    @Test
    fun `to should handle JSON with missing fields`() {
        // Given
        val jsonString = """
            {
                "rates": [
                    {"countryIdentifier": "USD", "currencyRate": 1.0}
                ]
            }
        """.trimIndent()

        // When
        val result = mapper.to(jsonString)
        //Then
        assertEquals(0, result.lastFetchTimeStamp)
        assertEquals(1, result.rates.size)
    }

    @Test
    fun `to should handle JSON with empty rates array`() {
        // Given
        val jsonString = """
            {
                "lastFetchTimeStamp": 1678886400000,
                "rates": []
            }
        """.trimIndent()

        // When
        val result = mapper.to(jsonString)

        // Then
        assertEquals(1678886400000, result.lastFetchTimeStamp)
        assertEquals(0, result.rates.size)
    }


}