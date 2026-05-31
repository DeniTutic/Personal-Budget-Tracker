package com.example.mobilno_projekat.navigation

data class BottomNavItem(
    val label: String,
    val iconText: String,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", "H", Screen.Home.route),
    BottomNavItem("Transactions", "T", Screen.Transactions.route),
    BottomNavItem("Categories", "C", Screen.Categories.route),
    BottomNavItem("Budget", "B", Screen.Budget.route)
)

fun getBottomNavRoutes(): List<String> {
    return bottomNavItems.map { it.route }
}