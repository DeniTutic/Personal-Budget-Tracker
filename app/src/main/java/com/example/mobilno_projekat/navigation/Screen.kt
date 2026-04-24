package com.example.mobilno_projekat.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object Transactions : Screen("transactions_screen")
    data object AddTransaction : Screen("add_transaction_screen")
    data object Categories : Screen("categories_screen")
    data object Budget : Screen("budget_screen")

    data object TransactionDetails : Screen("transaction_details_screen/{transactionId}/{transactionTitle}") {
        fun createRoute(transactionId: Int, transactionTitle: String): String {
            return "transaction_details_screen/$transactionId/${Uri.encode(transactionTitle)}"
        }
    }
}

fun getBottomNavRoutes(): List<String> {
    return listOf(
        Screen.Home.route,
        Screen.Transactions.route,
        Screen.Categories.route,
        Screen.Budget.route
    )
}