package com.test.paypayassignment.core.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class RoundOffTest {

    @Test
    fun roundOffDecimal_should_round_up_to_3_decimal_places() {
        val result = roundOffDecimal(2.45678)
        assertEquals(2.457, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_handle_already_rounded_numbers() {
        val result = roundOffDecimal(1.123)
        assertEquals(1.123, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_correctly_round_a_whole_number() {
        val result = roundOffDecimal(5.0)
        assertEquals(5.000, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_round_up_correctly_for_halfway_values() {
        val result = roundOffDecimal(3.99999)
        assertEquals(4.000, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_handle_negative_numbers_correctly() {
        val result = roundOffDecimal(-1.5555)
        assertEquals(-1.556, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_handle_very_small_numbers() {
        val result = roundOffDecimal(0.001)
        assertEquals(0.001, result, 0.0001)
    }

    @Test
    fun roundOffDecimal_should_handle_very_large_numbers() {
        val result = roundOffDecimal(123456.789123)
        assertEquals(123456.789, result, 0.0001)
    }
}