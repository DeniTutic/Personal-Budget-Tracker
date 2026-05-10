package com.example.mobilno_projekat.ui.screens.home

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState

    data class Success(
        val totalBalance: Double
    ) : HomeUiState

    data class Error(
        val message: String
    ) : HomeUiState
}