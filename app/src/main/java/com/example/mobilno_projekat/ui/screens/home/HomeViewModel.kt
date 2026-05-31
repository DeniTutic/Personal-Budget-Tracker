package com.example.mobilno_projekat.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.model.datasource.network.dto.CreateHabitDto
import com.example.mobilno_projekat.model.datasource.network.mapper.toHabitModel
import com.example.mobilno_projekat.model.repository.budget.BudgetRepository
import com.example.mobilno_projekat.model.repository.habit.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeHomeBalance()
        loadApiHabits()
    }

    fun loadHome() {
        observeHomeBalance()
        loadApiHabits()
    }

    private fun observeHomeBalance() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                budgetRepository.observeHomeBalance().collect { balance ->
                    val currentState = _uiState.value

                    if (currentState is HomeUiState.Success) {
                        _uiState.value = currentState.copy(
                            totalBalance = balance
                        )
                    } else {
                        _uiState.value = HomeUiState.Success(
                            totalBalance = balance
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IllegalStateException) {
                _uiState.value = HomeUiState.Error(
                    e.message ?: "Failed to load home balance."
                )
            }
        }
    }

    private fun loadApiHabits() {
        viewModelScope.launch {
            try {
                val habits = habitRepository.getHabits().map { habit ->
                    habit.toHabitModel()
                }

                val currentState = _uiState.value

                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiHabits = habits,
                        apiMessage = "API connected successfully."
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                val currentState = _uiState.value

                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiMessage = "API server is not running."
                    )
                }
            } catch (e: IllegalStateException) {
                val currentState = _uiState.value

                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiMessage = e.message ?: "Failed to load API data."
                    )
                }
            }
        }
    }

    fun createApiHabit() {
        viewModelScope.launch {
            try {
                habitRepository.createHabit(
                    CreateHabitDto(
                        title = "Track daily spending",
                        description = "Review expenses and update budget tracker.",
                        frequency = "daily",
                        completed = false,
                        userId = 1
                    )
                )

                loadApiHabits()
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                val currentState = _uiState.value
                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiMessage = "Could not create API item. Server unavailable."
                    )
                }
            } catch (e: IllegalStateException) {
                val currentState = _uiState.value
                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiMessage = e.message ?: "Could not create API item."
                    )
                }
            }
        }
    }
}