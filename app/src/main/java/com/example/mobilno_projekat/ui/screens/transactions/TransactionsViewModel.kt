package com.example.mobilno_projekat.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.model.repository.budget.BudgetRepository
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Loading)
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionsUiState.Loading

            try {
                val transactions = repository.loadTransactions()
                _uiState.value = TransactionsUiState.Success(transactions)
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = TransactionsUiState.Error(e.message ?: "Failed to load transactions.")
            }
        }
    }

    fun clearTransactions() {
        viewModelScope.launch {
            try {
                repository.clearTransactions()
                loadTransactions()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = TransactionsUiState.Error(e.message ?: "Failed to clear transactions.")
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                repository.deleteTransaction(transaction)
                loadTransactions()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalArgumentException) {
                _uiState.value = TransactionsUiState.Error(
                    e.message ?: "Failed to delete transaction."
                )
            }
        }
    }
}