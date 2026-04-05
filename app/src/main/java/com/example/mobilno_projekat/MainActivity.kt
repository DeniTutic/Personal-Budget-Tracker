package com.example.mobilno_projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.add.AddTransactionScreen
import com.example.mobilno_projekat.ui.screens.budget.BudgetScreen
import com.example.mobilno_projekat.ui.screens.categories.CategoriesScreen
import com.example.mobilno_projekat.ui.screens.home.HomeScreen
import com.example.mobilno_projekat.ui.screens.transactions.TransactionsScreen
import com.example.mobilno_projekat.ui.theme.MobilnoProjekatTheme
import com.example.mobilno_projekat.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobilnoProjekatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var currentScreen by rememberSaveable { mutableStateOf(AppScreen.HOME) }

                    when (currentScreen) {
                        AppScreen.HOME -> HomeScreen(
                            totalBalance = 620.0,
                            onOpenScreen = { selectedScreen ->
                                currentScreen = selectedScreen
                            },
                            modifier = Modifier.padding(innerPadding)
                        )

                        AppScreen.TRANSACTIONS -> TransactionsScreen(
                            transactions = viewModel.transactions,
                            onBack = { currentScreen = AppScreen.HOME },
                            onClearAll = { viewModel.clearTransactions() },
                            modifier = Modifier.padding(innerPadding)
                        )

                        AppScreen.ADD_TRANSACTION -> AddTransactionScreen(
                            onSave = { title, amount ->
                                viewModel.addTransaction(title, amount)
                            },
                            onBack = { currentScreen = AppScreen.HOME },
                            modifier = Modifier.padding(innerPadding)
                        )

                        AppScreen.CATEGORIES -> CategoriesScreen(
                            categories = viewModel.categories,
                            onBack = { currentScreen = AppScreen.HOME },
                            modifier = Modifier.padding(innerPadding)
                        )

                        AppScreen.BUDGET -> BudgetScreen(
                            summary = viewModel.budgetSummary,
                            onBack = { currentScreen = AppScreen.HOME },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}