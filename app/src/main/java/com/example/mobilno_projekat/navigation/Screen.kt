package com.example.mobilno_projekat.navigation

sealed class Screen(val route: String) {

    data object SignUp : Screen("sign_up")

    data object Login : Screen("login")

    data object Home : Screen("home")

    data object Transactions : Screen("transactions")

    data object AddTransaction : Screen("add_transaction")

    data object Categories : Screen("categories")

    data object Budget : Screen("budget")

    data object TransactionDetails : Screen(
        "transaction_details/{transactionId}/{transactionTitle}"
    ) {
        fun createRoute(
            transactionId: Int,
            transactionTitle: String
        ): String {
            return "transaction_details/$transactionId/$transactionTitle"
        }
    }
}