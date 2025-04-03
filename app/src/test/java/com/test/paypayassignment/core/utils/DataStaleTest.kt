package com.test.paypayassignment.core.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.TimeUnit

class DataStaleTest {


    private val freshDataPeriod = 30L

    @Test
    fun isLocalDataOutDated_should_return_false_when_data_is_fresh() {
        val startTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)
        val endTime = System.currentTimeMillis()

        val result = isDataStale(endTime, startTime)

        assertFalse("Data should be considered fresh", result)
    }

    @Test
    fun isLocalDataOutdated_should_return_true_when_data_is_outdated() {
        val startTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(35)
        val endTime = System.currentTimeMillis()

        val result = isDataStale(endTime, startTime)
        println(result)
        assertTrue("Data should be considered outdated", result)
    }

    @Test
    fun isLocalDataOutdated_should_return_false_when_data_is_fresh() {
        val startTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(freshDataPeriod)
        val endTime = System.currentTimeMillis()

        val result = isDataStale(endTime, startTime)

        assertFalse("Data should be considered fresh when exactly at FreshDataPeriod", result)
    }

    @Test
    fun isLocalDataOutdated_should_return_false_when_time_difference_is_negative() {
        val startTime = System.currentTimeMillis()
        val endTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)

        val result = isDataStale(endTime, startTime)

        assertFalse("Negative time difference should not be considered outdated", result)
    }
}
