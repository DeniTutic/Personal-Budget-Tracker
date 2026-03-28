package com.example.mobilno_projekat.ui.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.model.Transaction
import com.example.mobilno_projekat.ui.screens.transactions.components.TransactionItem

@Composable
fun TransactionsScreen(
    transactions: List<Transaction>,
    onBack: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.headlineMedium
        )

        if (transactions.isEmpty()) {
            Text(
                text = "No transactions yet.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            transactions.forEach { transaction ->
                TransactionItem(transaction = transaction)
            }
        }

        Button(
            onClick = onClearAll,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Transactions")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}