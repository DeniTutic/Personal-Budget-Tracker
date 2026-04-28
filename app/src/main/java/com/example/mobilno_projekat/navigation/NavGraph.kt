package com.example.mobilno_projekat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.add.AddTransactionScreen
import com.example.mobilno_projekat.ui.screens.add.AddTransactionViewModel
import com.example.mobilno_projekat.ui.screens.budget.BudgetScreen
import com.example.mobilno_projekat.ui.screens.budget.BudgetViewModel
import com.example.mobilno_projekat.ui.screens.categories.CategoriesScreen
import com.example.mobilno_projekat.ui.screens.categories.CategoriesViewModel
import com.example.mobilno_projekat.ui.screens.details.TransactionDetailsScreen
import com.example.mobilno_projekat.ui.screens.home.HomeScreen
import com.example.mobilno_projekat.ui.screens.home.HomeViewModel
import com.example.mobilno_projekat.ui.screens.transactions.TransactionsScreen
import com.example.mobilno_projekat.ui.screens.transactions.TransactionsViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                viewModel = viewModel,
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
            val viewModel: TransactionsViewModel = hiltViewModel()

            TransactionsScreen(
                viewModel = viewModel,
                onTransactionClick = { transaction ->
                    navController.navigate(
                        Screen.TransactionDetails.createRoute(
                            transactionId = transaction.id,
                            transactionTitle = transaction.title
                        )
                    )
                },
                onBack = { navController.navigateUp() }
            )
        }

        composable(Screen.AddTransaction.route) {
            val viewModel: AddTransactionViewModel = hiltViewModel()

            AddTransactionScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Categories.route) {
            val viewModel: CategoriesViewModel = hiltViewModel()

            CategoriesScreen(
                viewModel = viewModel,
                onBack = { navController.navigateUp() }
            )
        }

        composable(Screen.Budget.route) {
            val viewModel: BudgetViewModel = hiltViewModel()

            BudgetScreen(
                viewModel = viewModel,
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