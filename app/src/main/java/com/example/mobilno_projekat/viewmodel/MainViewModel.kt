package com.example.mobilno_projekat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobilno_projekat.model.BudgetSummary
import com.example.mobilno_projekat.model.Category
import com.example.mobilno_projekat.model.Transaction

class MainViewModel : ViewModel() {

    var transactions by mutableStateOf(
        listOf(
            Transaction(1, "Groceries", 45.50, "Expense"),
            Transaction(2, "Bus Ticket", 3.00, "Expense"),
            Transaction(3, "Part-time Job", 120.00, "Income")
        )
    )
        private set

    var categories by mutableStateOf(
        listOf(
            Category(1, "Food"),
            Category(2, "Transport"),
            Category(3, "Bills"),
            Category(4, "Salary")
        )
    )
        private set

    var budgetSummary by mutableStateOf(
        BudgetSummary(
            monthlyBudget = 500.0,
            spent = 148.50
        )
    )
        private set

    fun addTransaction(title: String, amount: String) {
        val parsedAmount = amount.toDoubleOrNull() ?: return
        val newTransaction = Transaction(
            id = transactions.size + 1,
            title = title.trim(),
            amount = parsedAmount,
            type = "Expense"
        )
        transactions = transactions + newTransaction
    }

    fun clearTransactions() {
        transactions = emptyList()
    }
}