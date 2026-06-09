package com.example.mobilno_projekat.model.repository.budget

import com.example.mobilno_projekat.ui.screens.budget.model.BudgetSummary
import com.example.mobilno_projekat.ui.screens.categories.model.Category
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    fun observeHomeBalance(): Flow<Double>

    suspend fun loadHomeBalance(): Double

    suspend fun loadTransactions(): List<Transaction>

    suspend fun loadCategories(): List<Category>

    suspend fun loadBudgetSummary(): BudgetSummary

    suspend fun addTransaction(title: String, amount: String)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun clearTransactions()

    suspend fun addCategory(name: String)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(category: Category)
}