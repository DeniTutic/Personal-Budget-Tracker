package com.example.mobilno_projekat.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    repository: BudgetRepository
) : ViewModel() {

    val uiState: StateFlow<BudgetUiState> =
        repository.budgetSummary
            .map { summary ->
                BudgetUiState.Success(summary)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = BudgetUiState.Loading
            )
}