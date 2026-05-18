package com.example.mobilno_projekat.ui.screens.transactions

import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction

sealed interface TransactionsUiState {
    data object Init : TransactionsUiState
    data object Loading : TransactionsUiState

    data class Success(
        val transactions: List<Transaction>
    ) : TransactionsUiState

    data class Error(
        val message: String
    ) : TransactionsUiState
}