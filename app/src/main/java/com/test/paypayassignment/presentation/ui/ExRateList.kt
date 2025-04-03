package com.test.paypayassignment.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.paypayassignment.presentation.model.CurrencyRates

@Composable
fun ExRateList(conversionRates: List<CurrencyRates>) {

    // Conversion Grid
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        conversionRates.forEach { (currency, rate) ->
            item {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = currency, fontWeight = FontWeight.Bold)
                        Text(text =  rate.toString() )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExRateListPreview() {
    val conversionRates = listOf(
        CurrencyRates("USD", 1.0),
        CurrencyRates("EUR", 0.85),
        CurrencyRates("JPY", 110.0),
        CurrencyRates("GBP", 0.75),
        CurrencyRates("AUD", 1.35),
        CurrencyRates("CAD", 1.25),
        CurrencyRates("CHF", 0.95),)
    ExRateList(conversionRates)

}
