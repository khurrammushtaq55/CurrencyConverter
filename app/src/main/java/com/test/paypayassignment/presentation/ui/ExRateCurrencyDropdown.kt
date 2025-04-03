package com.test.paypayassignment.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ExRateCurrencyDropdown(
    selectedCurrency: String,
    options: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    Box(modifier = Modifier.wrapContentSize()) {
        var expanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedCurrency)
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onCurrencySelected(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExRateCurrencyDropdownPreview() {
    ExRateCurrencyDropdown(
        selectedCurrency = "USD",
        options = listOf("USD", "EUR", "JPY", "PKR"),
        onCurrencySelected = {}


    )
}