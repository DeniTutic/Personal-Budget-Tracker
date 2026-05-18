package com.example.mobilno_projekat.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.ui.screens.add.components.InputField

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                AddTransactionNavigationEvent.NavigateBack -> {
                    onBack()
                }
            }
        }
    }

    AddTransactionScreenContent(
        uiState = uiState,
        onAddTransaction = { title, amount ->
            viewModel.addTransaction(title, amount)
        },
        modifier = modifier
    )
}

@Composable
private fun AddTransactionScreenContent(
    uiState: AddTransactionUiState,
    onAddTransaction: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val isButtonEnabled by remember(title, amount, uiState) {
        derivedStateOf {
            title.isNotBlank() &&
                    amount.isNotBlank() &&
                    uiState != AddTransactionUiState.Loading
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Add Transaction")
        }

        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InputField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Title"
                )

                InputField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = "Amount"
                )

                when (uiState) {
                    AddTransactionUiState.Init -> {
                        Text("Enter transaction details.")
                    }

                    AddTransactionUiState.Loading -> {
                        Text("Saving transaction...")
                    }

                    AddTransactionUiState.Success -> {
                        Text("Transaction saved.")
                    }

                    is AddTransactionUiState.Error -> {
                        Text(uiState.message)
                    }
                }

                Button(
                    onClick = {
                        onAddTransaction(title, amount)
                    },
                    enabled = isButtonEnabled
                ) {
                    Text("Save")
                }
            }
        }
    }
}