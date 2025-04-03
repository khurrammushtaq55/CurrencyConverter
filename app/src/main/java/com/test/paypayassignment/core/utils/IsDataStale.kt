package com.test.paypayassignment.core.utils

import java.util.concurrent.TimeUnit

    fun isDataStale(endTime: Long, startTime: Long): Boolean {
        val milliseconds = endTime - startTime
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        return minutes > 30
    }
