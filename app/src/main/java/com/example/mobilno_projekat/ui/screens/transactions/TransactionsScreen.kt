package com.example.mobilno_projekat.ui.screens.transactions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
        TransactionsUiState.Loading -> {
            Text("Loading transactions...")
        }

        is TransactionsUiState.Error -> {
            Text(state.message)
        }

        is TransactionsUiState.Success -> {
            TransactionsScreenStateful(
                transactions = state.transactions,
                onTransactionClick = onTransactionClick,
                onBack = onBack,
                onClearAll = viewModel::clearTransactions,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun TransactionsScreenStateful(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    onBack: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf("All") }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val filteredTransactions by remember(transactions, searchQuery, selectedFilter) {
        derivedStateOf {
            transactions.filter { transaction ->
                val matchesQuery = transaction.title.contains(searchQuery, ignoreCase = true)
                val matchesFilter = selectedFilter == "All" || transaction.type == selectedFilter
                matchesQuery && matchesFilter
            }
        }
    }

    val totalAmount by remember(filteredTransactions) {
        derivedStateOf { filteredTransactions.sumOf { it.amount } }
    }

    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    TransactionsScreenContent(
        searchQuery = searchQuery,
        selectedFilter = selectedFilter,
        filteredTransactions = filteredTransactions,
        totalAmount = totalAmount,
        listState = listState,
        showScrollToTop = showScrollToTop,
        onSearchQueryChange = { searchQuery = it },
        onFilterSelected = { selectedFilter = it },
        onTransactionClick = onTransactionClick,
        onBack = onBack,
        onClearAll = onClearAll,
        onScrollToTop = {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        },
        modifier = modifier
    )
}

@Composable
private fun TransactionsScreenContent(
    searchQuery: String,
    selectedFilter: String,
    filteredTransactions: List<Transaction>,
    totalAmount: Double,
    listState: LazyListState,
    showScrollToTop: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onFilterSelected: (String) -> Unit,
    onTransactionClick: (Transaction) -> Unit,
    onBack: () -> Unit,
    onClearAll: () -> Unit,
    onScrollToTop: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "Expense", "Income")

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Transactions",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text("Search transactions") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filters, key = { it }) { filter ->
                        TransactionFilterChip(
                            label = filter,
                            selected = selectedFilter == filter,
                            onClick = { onFilterSelected(filter) }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Visible total: $${"%.2f".format(totalAmount)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (filteredTransactions.isEmpty()) {
                item {
                    Text(
                        text = "No transactions available.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                items(
                    items = filteredTransactions,
                    key = { it.id }
                ) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onClick = { onTransactionClick(transaction) }
                    )
                }
            }

            item {
                HorizontalDivider()
            }

            item {
                Button(
                    onClick = onClearAll,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Transactions")
                }
            }

            item {
                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }

        AnimatedVisibility(
            visible = showScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(onClick = onScrollToTop) {
                Text("Top")
            }
        }
    }
}