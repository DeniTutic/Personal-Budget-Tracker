package com.example.mobilno_projekat.ui.screens.categories

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
class CategoriesViewModel @Inject constructor(
    repository: BudgetRepository
) : ViewModel() {

    val uiState: StateFlow<CategoriesUiState> =
        repository.categories
            .map { categories ->
                CategoriesUiState.Success(categories)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CategoriesUiState.Loading
            )
}