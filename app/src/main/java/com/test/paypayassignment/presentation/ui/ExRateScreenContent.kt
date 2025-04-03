package com.test.paypayassignment.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.paypayassignment.core.ExRatesUiState
import com.test.paypayassignment.presentation.model.CurrencyRates


@Composable
fun ExRateScreenContent(
    outlinedText: String,
    onTextChanged: (String) -> Unit,
    selectedCurrency: String,
    currencyOptions: List<String>,
    onCurrencySelected: (String) -> Unit,
    uiState: ExRatesUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        // Enter Amount
        OutlinedTextField(
            value = outlinedText,
            onValueChange = onTextChanged,
            maxLines = 1,
            label = { Text(text = "Enter Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Currency Selector Dropdown
        ExRateCurrencyDropdown(
            selectedCurrency = selectedCurrency,
            options = currencyOptions,
            onCurrencySelected = onCurrencySelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // UI State Handling
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (uiState) {
                is ExRatesUiState.Empty -> {
                    Text(text = "Error: No Data Found", color = MaterialTheme.colors.error)
                }

                is ExRatesUiState.Failure -> {
                    val errorMessage = uiState.errorText
                    Text(text = "Error: $errorMessage", color = MaterialTheme.colors.error)
                }

                ExRatesUiState.Loading -> CircularProgressIndicator()

                is ExRatesUiState.Success -> {
                    ExRateList(conversionRates = uiState.rates)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExRateScreenPreview() {
    ExRateScreenContent(
        outlinedText = "50",
        onTextChanged = {},
        selectedCurrency = "USD",
        currencyOptions = listOf("USD", "EUR", "JPY"),
        onCurrencySelected = {},
        uiState = ExRatesUiState.Success(
            rates = listOf(
                CurrencyRates("USD", 1.0),
                CurrencyRates("EUR", 0.85),
                CurrencyRates("JPY", 110.0),
                CurrencyRates("GBP", 0.75),
                CurrencyRates("AUD", 1.35),
                CurrencyRates("CAD", 1.25),
                CurrencyRates("CHF", 0.95)
            )
        )
    )
}