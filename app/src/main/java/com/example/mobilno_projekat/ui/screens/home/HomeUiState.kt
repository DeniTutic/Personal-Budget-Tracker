package com.example.mobilno_projekat.ui.screens.home

import com.example.mobilno_projekat.model.datasource.network.model.HabitModel

sealed interface HomeUiState {
    data object Init : HomeUiState
    data object Loading : HomeUiState

    data class Success(
        val totalBalance: Double,
        val apiHabits: List<HabitModel> = emptyList(),
        val apiMessage: String = ""
    ) : HomeUiState

    data class Error(
        val message: String
    ) : HomeUiState
}