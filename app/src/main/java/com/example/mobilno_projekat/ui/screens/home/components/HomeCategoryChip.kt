package com.example.mobilno_projekat.ui.screens.home.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeCategoryChip(
    name: String
) {
    AssistChip(
        onClick = { },
        label = { Text(name) }
    )
}