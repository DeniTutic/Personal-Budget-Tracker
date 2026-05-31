package com.example.mobilno_projekat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.model.datasource.network.model.HabitModel
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.home.components.DashboardCard
import com.example.mobilno_projekat.ui.screens.home.components.HomeCategoryChip
import com.example.mobilno_projekat.ui.screens.home.components.HomeShortcutCard
import com.example.mobilno_projekat.ui.screens.home.components.ApiHabitCard

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
        HomeUiState.Init,
        HomeUiState.Loading -> {
            Text("Loading home data...")
        }

        is HomeUiState.Error -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                item {
                    Button(onClick = { viewModel.loadHome() }) {
                        Text("Retry")
                    }
                }
            }
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
                apiHabits = state.apiHabits,
                apiMessage = state.apiMessage,
                onOpenScreen = onOpenScreen,
                onRefreshApi = viewModel::loadHome,
                onCreateApiHabit = viewModel::createApiHabit,
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
    apiHabits: List<HabitModel>,
    apiMessage: String,
    onOpenScreen: (AppScreen) -> Unit,
    onRefreshApi: () -> Unit,
    onCreateApiHabit: () -> Unit,
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                userScrollEnabled = false
            ) {
                items(shortcuts.size) { index ->
                    val shortcut = shortcuts[index]

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.take(3).forEach { category ->
                    HomeCategoryChip(
                        name = category.name,
                        onClick = { onOpenScreen(AppScreen.CATEGORIES) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            Text(
                text = "API Budget Habits",
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            Text(
                text = apiMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (apiHabits.isEmpty()) {
            item {
                Text(
                    text = "No API habits loaded.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            items(apiHabits.size) { index ->
                val habit = apiHabits[index]

                ApiHabitCard(habit = habit)
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRefreshApi,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Refresh API")
                }

                Button(
                    onClick = onCreateApiHabit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Create API")
                }
            }
        }
    }
}