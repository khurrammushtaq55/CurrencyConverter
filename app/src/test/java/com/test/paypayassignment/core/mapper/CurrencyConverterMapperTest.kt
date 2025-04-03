package com.test.paypayassignment.core.mapper

import com.test.paypayassignment.presentation.model.CurrencyRates
import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyConverterMapperTest {

    private val mapper = CurrencyConverterMapper()

    @Test
    fun `to should correctly convert currencies`() {
        // Given
        val rates = listOf(
            CurrencyRates("USD", 1.0),
            CurrencyRates("EUR", 0.85),
            CurrencyRates("JPY", 110.0)
        )
        val fromRate = 1.0 // USD
        val amount = 10.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(3, convertedRates.size)
        assertEquals("USD", convertedRates[0].countryIdentifier)
        assertEquals(10.0, convertedRates[0].currencyRate, 0.001) // 10 USD

        assertEquals("EUR", convertedRates[1].countryIdentifier)
        assertEquals(8.5, convertedRates[1].currencyRate, 0.001) // 10 USD -> 8.5 EUR

        assertEquals("JPY", convertedRates[2].countryIdentifier)
        assertEquals(1100.0, convertedRates[2].currencyRate, 0.001) // 10 USD -> 1100 JPY
    }

    @Test
    fun `to should handle zero amount`() {
        // Given
        val rates = listOf(
            CurrencyRates("USD", 1.0),
            CurrencyRates("EUR", 0.85)
        )
        val fromRate = 1.0
        val amount = 0.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(2, convertedRates.size)
        assertEquals(0.0, convertedRates[0].currencyRate, 0.001)
        assertEquals(0.0, convertedRates[1].currencyRate, 0.001)
    }

    @Test
    fun `to should handle different from rate`() {
        // Given
        val rates = listOf(
            CurrencyRates("USD", 1.0),
            CurrencyRates("EUR", 0.85)
        )
        val fromRate = 0.85 // EUR
        val amount = 10.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(2, convertedRates.size)
        assertEquals(11.765, convertedRates[0].currencyRate, 0.001) // 10 EUR -> 11.764 USD
        assertEquals(10.0, convertedRates[1].currencyRate, 0.001) // 10 EUR -> 10 EUR
    }

    @Test
    fun `to should handle empty rates list`() {
        // Given
        val rates = emptyList<CurrencyRates>()
        val fromRate = 1.0
        val amount = 10.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(0, convertedRates.size)
    }

    @Test
    fun `to should handle negative amount`() {
        // Given
        val rates = listOf(
            CurrencyRates("USD", 1.0),
            CurrencyRates("EUR", 0.85)
        )
        val fromRate = 1.0 // USD
        val amount = -10.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(2, convertedRates.size)
        assertEquals(-10.0, convertedRates[0].currencyRate, 0.001) // -10 USD
        assertEquals(-8.5, convertedRates[1].currencyRate, 0.001) // -8.5 EUR
    }

    @Test
    fun `to should handle negative rate`() {
        // Given
        val rates = listOf(
            CurrencyRates("USD", -1.0),
            CurrencyRates("EUR", 0.85)
        )
        val fromRate = 1.0 // USD
        val amount = 10.0

        // When
        val convertedRates = mapper.to(rates, fromRate, amount)

        // Then
        assertEquals(2, convertedRates.size)
        assertEquals(-10.0, convertedRates[0].currencyRate, 0.001)
        assertEquals(8.5, convertedRates[1].currencyRate, 0.001)
    }
}