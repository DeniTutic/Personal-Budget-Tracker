package com.example.mobilno_projekat.model.repository.budget

import com.example.mobilno_projekat.model.data.local.dao.AccountDao
import com.example.mobilno_projekat.model.data.local.dao.BudgetDao
import com.example.mobilno_projekat.model.data.local.dao.CategoryDao
import com.example.mobilno_projekat.model.data.local.dao.SavingGoalDao
import com.example.mobilno_projekat.model.data.local.dao.TransactionDao
import com.example.mobilno_projekat.model.data.local.entity.CategoryEntity
import com.example.mobilno_projekat.model.data.local.entity.TransactionEntity
import com.example.mobilno_projekat.model.data.local.util.BudgetSeed
import com.example.mobilno_projekat.model.repository.mappers.toCategory
import com.example.mobilno_projekat.model.repository.mappers.toTransaction
import com.example.mobilno_projekat.ui.screens.budget.model.BudgetSummary
import com.example.mobilno_projekat.ui.screens.categories.model.Category
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val budgetDao: BudgetDao,
    private val accountDao: AccountDao,
    private val savingGoalDao: SavingGoalDao
) : BudgetRepository {

    private suspend fun seedDatabaseIfNeeded() {
        if (categoryDao.getCategoryCount() == 0) {
            categoryDao.insertCategories(BudgetSeed.categories)

            BudgetSeed.transactions.forEach { transaction ->
                transactionDao.insertTransaction(transaction)
            }

            budgetDao.insertBudget(BudgetSeed.budget)

            BudgetSeed.accounts.forEach { account ->
                accountDao.insertAccount(account)
            }

            BudgetSeed.savingGoals.forEach { goal ->
                savingGoalDao.insertSavingGoal(goal)
            }
        }
    }

    override fun observeHomeBalance(): Flow<Double> {
        return flow {
            seedDatabaseIfNeeded()

            emitAll(
                transactionDao.observeAllTransactions().map { transactions ->
                    val budget = budgetDao.getBudget()
                    val expenses = transactions
                        .filter { it.type == "Expense" }
                        .sumOf { it.amount }

                    (budget?.monthlyBudget ?: 0.0) - expenses
                }
            )
        }
    }

    override suspend fun loadHomeBalance(): Double = withContext(Dispatchers.IO) {
        delay(300)
        seedDatabaseIfNeeded()

        val budget = budgetDao.getBudget()
        val expenses = transactionDao.getTotalExpenses()

        (budget?.monthlyBudget ?: 0.0) - expenses
    }

    override suspend fun loadTransactions(): List<Transaction> = withContext(Dispatchers.IO) {
        delay(300)
        seedDatabaseIfNeeded()

        transactionDao.getAllTransactions().map { entity ->
            entity.toTransaction()
        }
    }

    override suspend fun loadCategories(): List<Category> = withContext(Dispatchers.IO) {
        delay(300)
        seedDatabaseIfNeeded()

        categoryDao.getAllCategories().map { entity ->
            entity.toCategory()
        }
    }

    override suspend fun loadBudgetSummary(): BudgetSummary = withContext(Dispatchers.IO) {
        delay(300)
        seedDatabaseIfNeeded()

        val budget = budgetDao.getBudget()
        val spent = transactionDao.getTotalExpenses()

        BudgetSummary(
            monthlyBudget = budget?.monthlyBudget ?: 0.0,
            spent = spent
        )
    }

    override suspend fun addTransaction(title: String, amount: String) {
        withContext(Dispatchers.IO) {
            delay(300)
            seedDatabaseIfNeeded()

            val parsedAmount = amount.toDoubleOrNull()
                ?: throw IllegalArgumentException("Amount must be a valid number.")

            transactionDao.insertTransaction(
                TransactionEntity(
                    title = title.trim(),
                    amount = parsedAmount,
                    type = "Expense",
                    categoryId = 1
                )
            )
        }
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            seedDatabaseIfNeeded()

            val existingTransaction = transactionDao.getTransactionById(transaction.id)
                ?: throw IllegalArgumentException("Transaction not found.")

            transactionDao.updateTransaction(
                existingTransaction.copy(
                    title = transaction.title,
                    amount = transaction.amount,
                    type = transaction.type
                )
            )
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            seedDatabaseIfNeeded()

            val existingTransaction = transactionDao.getTransactionById(transaction.id)
                ?: throw IllegalArgumentException("Transaction not found.")

            transactionDao.deleteTransaction(existingTransaction)
        }
    }

    override suspend fun clearTransactions() {
        withContext(Dispatchers.IO) {
            delay(300)
            transactionDao.deleteAllTransactions()
        }
    }

    override suspend fun addCategory(name: String) {
        withContext(Dispatchers.IO) {
            seedDatabaseIfNeeded()

            categoryDao.insertCategory(
                CategoryEntity(
                    name = name.trim()
                )
            )
        }
    }

    override suspend fun updateCategory(category: Category) {
        withContext(Dispatchers.IO) {
            seedDatabaseIfNeeded()

            categoryDao.updateCategory(
                CategoryEntity(
                    id = category.id,
                    name = category.name
                )
            )
        }
    }

    override suspend fun deleteCategory(category: Category) {
        withContext(Dispatchers.IO) {
            seedDatabaseIfNeeded()

            categoryDao.deleteCategory(
                CategoryEntity(
                    id = category.id,
                    name = category.name
                )
            )
        }
    }
}