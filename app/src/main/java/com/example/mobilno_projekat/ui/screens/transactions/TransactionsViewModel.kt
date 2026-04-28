package com.example.mobilno_projekat.ui.screens.transactions

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
class TransactionsViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    val uiState: StateFlow<TransactionsUiState> =
        repository.transactions
            .map { transactions ->
                TransactionsUiState.Success(transactions)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TransactionsUiState.Loading
            )

    fun clearTransactions() {
        repository.clearTransactions()
    }
}