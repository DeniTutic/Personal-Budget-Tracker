package com.example.mobilno_projekat.ui.screens.categories.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.ui.screens.categories.model.Category

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(16.dp)
        )
    }
}