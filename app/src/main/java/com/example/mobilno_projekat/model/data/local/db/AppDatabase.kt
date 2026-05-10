package com.example.mobilno_projekat.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilno_projekat.model.data.local.dao.AccountDao
import com.example.mobilno_projekat.model.data.local.dao.BudgetDao
import com.example.mobilno_projekat.model.data.local.dao.CategoryDao
import com.example.mobilno_projekat.model.data.local.dao.SavingGoalDao
import com.example.mobilno_projekat.model.data.local.dao.TransactionDao
import com.example.mobilno_projekat.model.data.local.entity.AccountEntity
import com.example.mobilno_projekat.model.data.local.entity.BudgetEntity
import com.example.mobilno_projekat.model.data.local.entity.CategoryEntity
import com.example.mobilno_projekat.model.data.local.entity.SavingGoalEntity
import com.example.mobilno_projekat.model.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class,
        AccountEntity::class,
        SavingGoalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun accountDao(): AccountDao
    abstract fun savingGoalDao(): SavingGoalDao
}