package com.example.mobilno_projekat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.add.AddTransactionScreen
import com.example.mobilno_projekat.ui.screens.budget.BudgetScreen
import com.example.mobilno_projekat.ui.screens.categories.CategoriesScreen
import com.example.mobilno_projekat.ui.screens.details.TransactionDetailsScreen
import com.example.mobilno_projekat.ui.screens.home.HomeScreen
import com.example.mobilno_projekat.ui.screens.transactions.TransactionsScreen
import com.example.mobilno_projekat.viewmodel.MainViewModel
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                totalBalance = 620.0,
                onOpenScreen = { selectedScreen ->
                    when (selectedScreen) {
                        AppScreen.HOME -> navController.navigate(Screen.Home.route)
                        AppScreen.TRANSACTIONS -> navController.navigate(Screen.Transactions.route)
                        AppScreen.ADD_TRANSACTION -> navController.navigate(Screen.AddTransaction.route)
                        AppScreen.CATEGORIES -> navController.navigate(Screen.Categories.route)
                        AppScreen.BUDGET -> navController.navigate(Screen.Budget.route)
                    }
                }
            )
        }

        composable(Screen.Transactions.route) {
            TransactionsScreen(
                transactions = viewModel.transactions,
                onTransactionClick = { transaction: Transaction ->
                    navController.navigate(
                        Screen.TransactionDetails.createRoute(
                            transactionId = transaction.id,
                            transactionTitle = transaction.title
                        )
                    )
                },
                onBack = { navController.navigateUp() },
                onClearAll = { viewModel.clearTransactions() }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                onSave = { title, amount ->
                    viewModel.addTransaction(title, amount)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                categories = viewModel.categories,
                onBack = { navController.navigateUp() }
            )
        }

        composable(Screen.Budget.route) {
            BudgetScreen(
                summary = viewModel.budgetSummary,
                onBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.TransactionDetails.route,
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.IntType
                },
                navArgument("transactionTitle") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: 0
            val transactionTitle = backStackEntry.arguments?.getString("transactionTitle").orEmpty()

            TransactionDetailsScreen(
                transactionId = transactionId,
                transactionTitle = transactionTitle,
                onBack = { navController.navigateUp() }
            )
        }
    }
}