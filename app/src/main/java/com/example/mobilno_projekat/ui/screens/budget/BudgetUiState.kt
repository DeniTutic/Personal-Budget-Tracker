package com.example.mobilno_projekat.ui.screens.budget

import com.example.mobilno_projekat.ui.screens.budget.model.BudgetSummary

sealed interface BudgetUiState {
    data object Loading : BudgetUiState
    data class Success(
        val summary: BudgetSummary
    ) : BudgetUiState
    data class Error(
        val message: String
    ) : BudgetUiState
}