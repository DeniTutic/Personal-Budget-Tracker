package com.example.mobilno_projekat.ui.screens.categories

import com.example.mobilno_projekat.ui.screens.categories.model.Category

sealed interface CategoriesUiState {
    data object Init : CategoriesUiState
    data object Loading : CategoriesUiState

    data class Success(
        val categories: List<Category>
    ) : CategoriesUiState

    data class Error(
        val message: String
    ) : CategoriesUiState
}