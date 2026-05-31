package com.example.mobilno_projekat.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.model.datasource.network.dto.CreateHabitDto
import com.example.mobilno_projekat.model.datasource.network.mapper.toHabitModel
import com.example.mobilno_projekat.model.repository.budget.BudgetRepository
import com.example.mobilno_projekat.model.repository.habit.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
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
        loadHome()
    }

    fun loadHome() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                val balanceDeferred = async {
                    budgetRepository.loadHomeBalance()
                }

                val habitsResult = runCatching {
                    habitRepository.getHabits().map { habit ->
                        habit.toHabitModel()
                    }
                }

                val apiHabits = habitsResult.getOrElse {
                    emptyList()
                }

                val apiMessage = if (habitsResult.isSuccess) {
                    "API connected successfully."
                } else {
                    "API server is not running."
                }

                _uiState.value = HomeUiState.Success(
                    totalBalance = balanceDeferred.await(),
                    apiHabits = apiHabits,
                    apiMessage = apiMessage
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                _uiState.value = HomeUiState.Error("Network error. Check your connection.")
            } catch (e: IllegalStateException) {
                _uiState.value = HomeUiState.Error(e.message ?: "Failed to load home data.")
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

                loadHome()
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