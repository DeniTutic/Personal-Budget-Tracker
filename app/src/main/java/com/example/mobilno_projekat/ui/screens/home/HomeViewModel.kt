package com.example.mobilno_projekat.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilno_projekat.model.datasource.network.dto.CreateHabitDto
import com.example.mobilno_projekat.model.datasource.network.mapper.toHabitModel
import com.example.mobilno_projekat.model.datasource.network.model.HabitModel
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

    private val createdApiHabits = mutableListOf<HabitModel>()

    private val sampleHabits = listOf(
        CreateHabitDto(
            title = "Track daily spending",
            description = "Review expenses and update budget tracker.",
            frequency = "daily",
            completed = false,
            userId = 1
        ),
        CreateHabitDto(
            title = "Check weekly budget",
            description = "Open the app and review spending for this week.",
            frequency = "weekly",
            completed = false,
            userId = 1
        ),
        CreateHabitDto(
            title = "Save for emergencies",
            description = "Put aside a small amount for emergency savings.",
            frequency = "monthly",
            completed = false,
            userId = 1
        )
    )

    private var nextHabitIndex = 0

    init {
        observeHomeBalance()
    }

    fun loadHome() {
        observeHomeBalance()
    }

    private fun observeHomeBalance() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                budgetRepository.observeHomeBalance().collect { balance ->
                    val currentState = _uiState.value

                    if (currentState is HomeUiState.Success) {
                        _uiState.value = currentState.copy(
                            totalBalance = balance,
                            apiHabits = createdApiHabits,
                            apiMessage = currentState.apiMessage
                        )
                    } else {
                        _uiState.value = HomeUiState.Success(
                            totalBalance = balance,
                            apiHabits = emptyList(),
                            apiMessage = "API ready. Press Create API to add data."
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

    fun createApiHabit() {
        viewModelScope.launch {
            try {
                val habitToCreate = sampleHabits[nextHabitIndex % sampleHabits.size]
                nextHabitIndex++

                val createdHabit = habitRepository.createHabit(habitToCreate).toHabitModel()
                createdApiHabits.add(createdHabit)

                val currentState = _uiState.value

                if (currentState is HomeUiState.Success) {
                    _uiState.value = currentState.copy(
                        apiHabits = createdApiHabits.toList(),
                        apiMessage = "API item created successfully."
                    )
                }
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