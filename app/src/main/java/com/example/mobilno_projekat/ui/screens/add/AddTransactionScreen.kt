package com.example.mobilno_projekat.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    var title by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }

    val amountNumber by remember(amount) {
        derivedStateOf { amount.toDoubleOrNull() }
    }

    val titleError by remember(title) {
        derivedStateOf {
            if (title.isBlank()) "Title cannot be empty." else null
        }
    }

    val amountError by remember(amount, amountNumber) {
        derivedStateOf {
            when {
                amount.isBlank() -> "Amount cannot be empty."
                amountNumber == null -> "Amount must be a valid number."
                amountNumber!! <= 0 -> "Amount must be greater than 0."
                else -> null
            }
        }
    }

    val isValid by remember(titleError, amountError) {
        derivedStateOf { titleError == null && amountError == null }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                AddTransactionNavigationEvent.NavigateBack -> onBack()
            }
        }
    }

    AddTransactionScreenContent(
        title = title,
        amount = amount,
        titleError = titleError,
        amountError = amountError,
        uiState = uiState,
        isValid = isValid,
        onTitleChange = { title = it },
        onAmountChange = { amount = it },
        onSaveClick = {
            viewModel.addTransaction(title, amount)
        },
        onBack = onBack,
        onDismissError = viewModel::resetUiState,
        modifier = modifier
    )
}

@Composable
private fun AddTransactionScreenContent(
    title: String,
    amount: String,
    titleError: String?,
    amountError: String?,
    uiState: AddTransactionUiState,
    isValid: Boolean,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBack: () -> Unit,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            onValueChange = onTitleChange
        )

        if (titleError != null) {
            Text(text = titleError, color = Color.Red)
        }

        InputField(
            value = amount,
            label = "Amount",
            onValueChange = onAmountChange
        )

        if (amountError != null) {
            Text(text = amountError, color = Color.Red)
        }

        when (uiState) {
            AddTransactionUiState.Loading -> {
                Text("Saving transaction...")
            }

            is AddTransactionUiState.Error -> {
                Text(text = uiState.message, color = Color.Red)
                Button(onClick = onDismissError) {
                    Text("Dismiss")
                }
            }

            else -> Unit
        }

        Button(
            onClick = onSaveClick,
            enabled = isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Transaction")
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}