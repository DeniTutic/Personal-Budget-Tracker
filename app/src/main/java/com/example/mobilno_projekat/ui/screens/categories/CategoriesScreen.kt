package com.example.mobilno_projekat.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.ui.screens.categories.components.CategoryItem
import com.example.mobilno_projekat.ui.screens.categories.model.Category

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        CategoriesUiState.Loading -> {
            Text("Loading categories...")
        }

        is CategoriesUiState.Error -> {
            Text(state.message)
        }

        is CategoriesUiState.Success -> {
            CategoriesScreenStateful(
                categories = state.categories,
                onBack = onBack,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CategoriesScreenStateful(
    categories: List<Category>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategoryName by rememberSaveable { mutableStateOf("") }

    val filteredCategories by remember(categories, searchQuery) {
        derivedStateOf {
            categories.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    CategoriesScreenContent(
        searchQuery = searchQuery,
        selectedCategoryName = selectedCategoryName,
        filteredCategories = filteredCategories,
        onSearchQueryChange = { searchQuery = it },
        onCategoryClick = { selectedCategoryName = it.name },
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun CategoriesScreenContent(
    searchQuery: String,
    selectedCategoryName: String,
    filteredCategories: List<Category>,
    onSearchQueryChange: (String) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Search categories") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (selectedCategoryName.isNotBlank()) {
            item {
                Text(
                    text = "Selected: $selectedCategoryName",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (filteredCategories.isEmpty()) {
            item {
                Text(
                    text = "No categories available.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            items(
                items = filteredCategories,
                key = { it.id }
            ) { category ->
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }

        item {
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}