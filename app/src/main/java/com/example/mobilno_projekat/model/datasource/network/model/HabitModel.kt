package com.example.mobilno_projekat.model.datasource.network.model

data class HabitModel(
    val id: Int,
    val title: String,
    val description: String,
    val frequency: String,
    val completed: Boolean
)