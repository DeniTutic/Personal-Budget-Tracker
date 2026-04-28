package com.example.mobilno_projekat.ui.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddTransactionUiState>(AddTransactionUiState.Init)
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<AddTransactionNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun addTransaction(title: String, amount: String) {
        viewModelScope.launch {
            _uiState.value = AddTransactionUiState.Loading

            val parsedAmount = amount.toDoubleOrNull()

            when {
                title.isBlank() -> {
                    _uiState.value = AddTransactionUiState.Error("Title cannot be empty.")
                }

                amount.isBlank() -> {
                    _uiState.value = AddTransactionUiState.Error("Amount cannot be empty.")
                }

                parsedAmount == null -> {
                    _uiState.value = AddTransactionUiState.Error("Amount must be a valid number.")
                }

                parsedAmount <= 0 -> {
                    _uiState.value = AddTransactionUiState.Error("Amount must be greater than 0.")
                }

                else -> {
                    repository.addTransaction(title, amount)
                    _uiState.value = AddTransactionUiState.Success
                    _navigationEvent.send(AddTransactionNavigationEvent.NavigateBack)
                }
            }
        }
    }

    fun resetUiState() {
        _uiState.value = AddTransactionUiState.Init
    }
}