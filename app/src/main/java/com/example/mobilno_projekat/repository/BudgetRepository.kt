package com.example.mobilno_projekat.repository

import com.example.mobilno_projekat.ui.screens.budget.model.BudgetSummary
import com.example.mobilno_projekat.ui.screens.categories.model.Category
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor() {

    private val _transactions = MutableStateFlow(
        listOf(
            Transaction(1, "Groceries", 45.50, "Expense"),
            Transaction(2, "Bus Ticket", 3.00, "Expense"),
            Transaction(3, "Part-time Job", 120.00, "Income")
        )
    )
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _categories = MutableStateFlow(
        listOf(
            Category(1, "Food"),
            Category(2, "Transport"),
            Category(3, "Bills"),
            Category(4, "Salary")
        )
    )
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _budgetSummary = MutableStateFlow(
        BudgetSummary(
            monthlyBudget = 500.0,
            spent = 148.50
        )
    )
    val budgetSummary: StateFlow<BudgetSummary> = _budgetSummary.asStateFlow()

    suspend fun loadHomeBalance(): Double = withContext(Dispatchers.IO) {
        delay(700)
        620.0
    }

    suspend fun loadTransactions(): List<Transaction> = withContext(Dispatchers.IO) {
        delay(700)
        _transactions.value
    }

    suspend fun loadCategories(): List<Category> = withContext(Dispatchers.IO) {
        delay(700)
        _categories.value
    }

    suspend fun loadBudgetSummary(): BudgetSummary = withContext(Dispatchers.IO) {
        delay(700)
        _budgetSummary.value
    }

    suspend fun addTransaction(title: String, amount: String) = withContext(Dispatchers.IO) {
        delay(500)

        val parsedAmount = amount.toDoubleOrNull()
            ?: throw IllegalArgumentException("Amount must be a valid number.")

        val newTransaction = Transaction(
            id = (_transactions.value.maxOfOrNull { it.id } ?: 0) + 1,
            title = title.trim(),
            amount = parsedAmount,
            type = "Expense"
        )

        _transactions.value = _transactions.value + newTransaction
    }

    suspend fun clearTransactions() = withContext(Dispatchers.IO) {
        delay(400)
        _transactions.value = emptyList()
    }
}