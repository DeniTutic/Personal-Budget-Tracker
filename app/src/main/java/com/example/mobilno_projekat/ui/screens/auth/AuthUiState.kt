package com.example.mobilno_projekat.ui.screens.auth

sealed interface AuthUiState {
    data object Init : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
}