package com.example.mobilno_projekat.ui.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.ui.screens.transactions.components.TransactionFilterChip
import com.example.mobilno_projekat.ui.screens.transactions.components.TransactionItem
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import kotlinx.coroutines.launch

@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel,
    onTransactionClick: (Transaction) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        TransactionsUiState.Init,
        TransactionsUiState.Loading -> {
            Text("Loading transactions...")
        }

        is TransactionsUiState.Error -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(state.message)
                }

                item {
                    Button(onClick = { viewModel.loadTransactions() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is TransactionsUiState.Success -> {
            TransactionsScreenContent(
                transactions = state.transactions,
                onTransactionClick = onTransactionClick,
                onClearTransactions = { viewModel.clearTransactions() },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun TransactionsScreenContent(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    onClearTransactions: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val coroutineScope = rememberCoroutineScope()

    val filters = remember {
        listOf("All", "Income", "Expense")
    }

    val filteredTransactions by remember(transactions, selectedFilter) {
        derivedStateOf {
            if (selectedFilter == "All") {
                transactions
            } else {
                transactions.filter { it.type == selectedFilter }
            }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Transactions")
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    TransactionFilterChip(
                        label = filter,
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        onClearTransactions()
                    }
                }
            ) {
                Text("Clear Transactions")
            }
        }

        if (filteredTransactions.isEmpty()) {
            item {
                Text("No transactions available.")
            }
        } else {
            items(
                items = filteredTransactions,
                key = { it.id }
            ) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { onTransactionClick(transaction) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}