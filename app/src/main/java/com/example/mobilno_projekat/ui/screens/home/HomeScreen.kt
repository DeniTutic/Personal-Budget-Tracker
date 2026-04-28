package com.example.mobilno_projekat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.home.components.DashboardCard
import com.example.mobilno_projekat.ui.screens.home.components.HomeCategoryChip
import com.example.mobilno_projekat.ui.screens.home.components.HomeShortcutCard

private data class ShortcutItem(
    val id: Int,
    val title: String,
    val target: AppScreen
)

private data class HomeCategoryItem(
    val id: Int,
    val name: String
)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onOpenScreen: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        HomeUiState.Loading -> {
            Text("Loading...")
        }

        is HomeUiState.Success -> {
            val balanceLabel by remember(state.totalBalance) {
                derivedStateOf {
                    if (state.totalBalance >= 0) "Available Balance" else "Negative Balance"
                }
            }

            val shortcuts = remember {
                listOf(
                    ShortcutItem(1, "Transactions", AppScreen.TRANSACTIONS),
                    ShortcutItem(2, "Add", AppScreen.ADD_TRANSACTION),
                    ShortcutItem(3, "Categories", AppScreen.CATEGORIES),
                    ShortcutItem(4, "Budget", AppScreen.BUDGET)
                )
            }

            val categories = remember {
                listOf(
                    HomeCategoryItem(1, "Food"),
                    HomeCategoryItem(2, "Transport"),
                    HomeCategoryItem(3, "Bills"),
                    HomeCategoryItem(4, "Salary"),
                    HomeCategoryItem(5, "Shopping")
                )
            }

            HomeScreenContent(
                totalBalance = state.totalBalance,
                balanceLabel = balanceLabel,
                shortcuts = shortcuts,
                categories = categories,
                onOpenScreen = onOpenScreen,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    totalBalance: Double,
    balanceLabel: String,
    shortcuts: List<ShortcutItem>,
    categories: List<HomeCategoryItem>,
    onOpenScreen: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Personal Budget Tracker",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DashboardCard(
                title = balanceLabel,
                value = "$${"%.2f".format(totalBalance)}"
            )
        }

        item {
            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = shortcuts,
                    key = { it.id }
                ) { shortcut ->
                    HomeShortcutCard(
                        title = shortcut.title,
                        onClick = { onOpenScreen(shortcut.target) }
                    )
                }
            }
        }

        item {
            Text(
                text = "Popular Categories",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = categories,
                    key = { it.id }
                ) { category ->
                    HomeCategoryChip(name = category.name)
                }
            }
        }
    }
}