package com.example.mobilno_projekat.ui.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import com.example.mobilno_projekat.ui.screens.transactions.components.TransactionItem

@Composable
fun TransactionsScreen(
    transactions: List<Transaction>,
    onBack: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isEmpty by remember(transactions) {
        derivedStateOf { transactions.isEmpty() }
    }

    val transactionCount by remember(transactions) {
        derivedStateOf { transactions.size }
    }

    val totalExpense by remember(transactions) {
        derivedStateOf { transactions.sumOf { it.amount } }
    }

    TransactionsScreenContent(
        transactions = transactions,
        isEmpty = isEmpty,
        transactionCount = transactionCount,
        totalExpense = totalExpense,
        onBack = onBack,
        onClearAll = onClearAll,
        modifier = modifier
    )
}

@Composable
private fun TransactionsScreenContent(
    transactions: List<Transaction>,
    isEmpty: Boolean,
    transactionCount: Int,
    totalExpense: Double,
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

        Text(text = "Total items: $transactionCount")
        Text(text = "Total amount: $${"%.2f".format(totalExpense)}")

        if (isEmpty) {
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