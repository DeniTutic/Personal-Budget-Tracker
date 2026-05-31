package com.example.mobilno_projekat.model.di

import android.content.Context
import androidx.room.Room
import com.example.mobilno_projekat.model.data.local.dao.AccountDao
import com.example.mobilno_projekat.model.data.local.dao.BudgetDao
import com.example.mobilno_projekat.model.data.local.dao.CategoryDao
import com.example.mobilno_projekat.model.data.local.dao.SavingGoalDao
import com.example.mobilno_projekat.model.data.local.dao.TransactionDao
import com.example.mobilno_projekat.model.data.local.dao.UserDao
import com.example.mobilno_projekat.model.data.local.db.AppDatabase
import com.example.mobilno_projekat.model.repository.auth.AuthRepository
import com.example.mobilno_projekat.model.repository.auth.AuthRepositoryImpl
import com.example.mobilno_projekat.model.repository.budget.BudgetRepository
import com.example.mobilno_projekat.model.repository.budget.BudgetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "personal_budget_tracker_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(database: AppDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: AppDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    @Singleton
    fun provideSavingGoalDao(database: AppDatabase): SavingGoalDao {
        return database.savingGoalDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideBudgetRepository(
        transactionDao: TransactionDao,
        categoryDao: CategoryDao,
        budgetDao: BudgetDao,
        accountDao: AccountDao,
        savingGoalDao: SavingGoalDao
    ): BudgetRepository {
        return BudgetRepositoryImpl(
            transactionDao = transactionDao,
            categoryDao = categoryDao,
            budgetDao = budgetDao,
            accountDao = accountDao,
            savingGoalDao = savingGoalDao
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(userDao)
    }
}