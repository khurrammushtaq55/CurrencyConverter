package com.test.paypayassignment.core.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConvertCurrencyTest {

    @Test
    fun convertCurrency_should_correctly_convert_between_currencies() {
        // Given: Converting 100 units from a currency with rate 1.5 to a currency with rate 0.75
        val amount = 100.0
        val fromCurrencyRate = 1.5
        val toCurrencyRate = 0.75

        val result = convertCurrency(amount, fromCurrencyRate, toCurrencyRate)

        val expected = roundOffDecimal((1.0 / fromCurrencyRate * toCurrencyRate) * amount)
        assertEquals(expected, result, 0.001)
    }

    @Test
    fun convertCurrency_should_return_the_same_amount_if_converting_between_same_rates() {
        // Given: Converting 50 units from a currency with rate 2.0 to the same rate (2.0)
        val amount = 50.0
        val rate = 2.0

        val result = convertCurrency(amount, rate, rate)

        assertEquals(amount, result, 0.001)
    }

    @Test
    fun convertCurrency_Should_Handle_Small_Fractional_Conversions() {
        // Given: A very small amount conversion
        val amount = 0.01
        val fromCurrencyRate = 1.2
        val toCurrencyRate = 0.8

        val result = convertCurrency(amount, fromCurrencyRate, toCurrencyRate)

        val expected = roundOffDecimal((1.0 / fromCurrencyRate * toCurrencyRate) * amount)
        assertEquals(expected, result, 0.001)
    }

    @Test
    fun convertCurrency_Should_Handle_Large_Fractional_Conversions() {
        // Given: Large amount conversion
        val amount = 1_000_000.0
        val fromCurrencyRate = 3.0
        val toCurrencyRate = 1.5

        val result = convertCurrency(amount, fromCurrencyRate, toCurrencyRate)

        val expected = roundOffDecimal((1.0 / fromCurrencyRate * toCurrencyRate) * amount)
        assertEquals(expected, result, 0.001)
    }

    @Test
    fun convertCurrency_Should_Handle_Zero_Amount() {
        // Given: Amount is zero
        val amount = 0.0
        val fromCurrencyRate = 2.0
        val toCurrencyRate = 3.0

        val result = convertCurrency(amount, fromCurrencyRate, toCurrencyRate)

        assertEquals(0.0, result, 0.001)
    }
}