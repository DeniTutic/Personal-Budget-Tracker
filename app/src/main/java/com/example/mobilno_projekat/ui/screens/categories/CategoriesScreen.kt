package com.example.mobilno_projekat.ui.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.ui.screens.categories.model.Category
import com.example.mobilno_projekat.ui.screens.categories.components.CategoryItem

@Composable
fun CategoriesScreen(
    categories: List<Category>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isEmpty by remember(categories) {
        derivedStateOf { categories.isEmpty() }
    }

    val categoryCount by remember(categories) {
        derivedStateOf { categories.size }
    }

    CategoriesScreenContent(
        categories = categories,
        isEmpty = isEmpty,
        categoryCount = categoryCount,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun CategoriesScreenContent(
    categories: List<Category>,
    isEmpty: Boolean,
    categoryCount: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = "Total categories: $categoryCount")

        if (isEmpty) {
            Text("No categories available.")
        } else {
            categories.forEach { category ->
                CategoryItem(category = category)
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}