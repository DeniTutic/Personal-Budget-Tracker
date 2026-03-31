package com.example.mobilno_projekat.model

data class BudgetSummary(
    val monthlyBudget: Double,
    val spent: Double
) {
    val remaining: Double
        get() = monthlyBudget - spent
}