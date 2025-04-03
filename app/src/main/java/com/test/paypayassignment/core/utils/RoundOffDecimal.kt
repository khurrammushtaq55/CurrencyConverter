package com.test.paypayassignment.core.utils

import android.util.Log
import java.util.Locale

fun roundOffDecimal(number: Double): Double {
    return try {
        String.format(Locale.US, "%.3f", number).toDouble()
    } catch (e: Exception) {
        Log.e("roundOffDecimal", e.message.toString())
        number
    }
}
