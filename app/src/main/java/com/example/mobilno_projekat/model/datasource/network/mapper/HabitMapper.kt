package com.example.mobilno_projekat.model.datasource.network.mapper

import com.example.mobilno_projekat.model.datasource.network.dto.HabitDto
import com.example.mobilno_projekat.model.datasource.network.model.HabitModel

fun HabitDto.toHabitModel(): HabitModel {
    return HabitModel(
        id = id,
        title = title.ifBlank { "Budget Habit #$id" },
        description = description.ifBlank { "No description available." },
        frequency = frequency.ifBlank { "Not set" },
        completed = completed
    )
}