package com.example.mobilno_projekat.ui.screens.add

sealed interface AddTransactionUiState {
    data object Init : AddTransactionUiState
    data object Loading : AddTransactionUiState
    data object Success : AddTransactionUiState

    data class Error(
        val message: String
    ) : AddTransactionUiState
}