package com.example.mobilno_projekat.model.data.local.util

import com.example.mobilno_projekat.model.data.local.entity.AccountEntity
import com.example.mobilno_projekat.model.data.local.entity.BudgetEntity
import com.example.mobilno_projekat.model.data.local.entity.CategoryEntity
import com.example.mobilno_projekat.model.data.local.entity.SavingGoalEntity
import com.example.mobilno_projekat.model.data.local.entity.TransactionEntity

object BudgetSeed {

    val categories = listOf(
        CategoryEntity(id = 1, name = "Food"),
        CategoryEntity(id = 2, name = "Transport"),
        CategoryEntity(id = 3, name = "Bills"),
        CategoryEntity(id = 4, name = "Salary"),
        CategoryEntity(id = 5, name = "Shopping")
    )

    val transactions = listOf(
        TransactionEntity(
            id = 1,
            title = "Groceries",
            amount = 45.50,
            type = "Expense",
            categoryId = 1
        ),
        TransactionEntity(
            id = 2,
            title = "Bus Ticket",
            amount = 3.00,
            type = "Expense",
            categoryId = 2
        ),
        TransactionEntity(
            id = 3,
            title = "Part-time Job",
            amount = 120.00,
            type = "Income",
            categoryId = 4
        )
    )

    val budget = BudgetEntity(
        id = 1,
        monthlyBudget = 500.0
    )

    val accounts = listOf(
        AccountEntity(
            id = 1,
            name = "Cash",
            balance = 220.0
        ),
        AccountEntity(
            id = 2,
            name = "Bank Account",
            balance = 400.0
        )
    )

    val savingGoals = listOf(
        SavingGoalEntity(
            id = 1,
            title = "Emergency Fund",
            targetAmount = 1000.0,
            currentAmount = 250.0
        )
    )
}