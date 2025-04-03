package com.test.paypayassignment.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.test.paypayassignment.presentation.viewmodel.ExRateViewModel

@Composable
fun ExRateScreen(viewModel: ExRateViewModel) {
    val uiState by viewModel.conversion.collectAsState()
    var outlinedText by remember { mutableStateOf("1") }
    var selectedCurrency by remember { mutableStateOf(viewModel.lastSelectedCurrency) }

    ExRateScreenContent(
        outlinedText = outlinedText,
        onTextChanged = {
            outlinedText = it
            if (outlinedText.isNotEmpty()) {
                viewModel.onChange(outlinedText)
            } else {
                viewModel.resetCalculations()
            }
        },
        selectedCurrency = selectedCurrency,
        currencyOptions = viewModel.lastFetchedCurrencies,
        onCurrencySelected = { newCurrency ->
            selectedCurrency = newCurrency
            viewModel.lastSelectedCurrency = newCurrency
            viewModel.resetCalculations()
            outlinedText = "1"
        },
        uiState = uiState
    )
}
