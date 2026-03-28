package com.example.mobilno_projekat.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.ui.screens.add.components.InputField

@Composable
fun AddTransactionScreen(
    onSave: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val amountNumber = amount.toDoubleOrNull()
    val isValid = title.isNotBlank() && amount.isNotBlank() && amountNumber != null && amountNumber > 0

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Add Transaction",
            style = MaterialTheme.typography.headlineMedium
        )

        InputField(
            value = title,
            label = "Title",
            onValueChange = { title = it }
        )

        InputField(
            value = amount,
            label = "Amount",
            onValueChange = { amount = it }
        )

        if (amount.isNotBlank() && amountNumber == null) {
            Text(
                text = "Amount must be a valid number.",
                color = Color.Red
            )
        }

        if (amountNumber != null && amountNumber <= 0) {
            Text(
                text = "Amount must be greater than 0.",
                color = Color.Red
            )
        }

        Button(
            onClick = {
                onSave(title, amount)
                title = ""
                amount = ""
            },
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Transaction")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}