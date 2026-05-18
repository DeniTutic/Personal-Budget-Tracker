package com.example.mobilno_projekat.ui.screens.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilno_projekat.ui.screens.budget.components.BudgetInfoCard
import com.example.mobilno_projekat.ui.screens.budget.model.BudgetSummary

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        BudgetUiState.Init,
        BudgetUiState.Loading -> {
            Text("Loading budget...")
        }

        is BudgetUiState.Error -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(state.message)
                }

                item {
                    Button(onClick = { viewModel.loadBudget() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is BudgetUiState.Success -> {
            BudgetScreenContent(
                summary = state.summary,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BudgetScreenContent(
    summary: BudgetSummary,
    modifier: Modifier = Modifier
) {
    val remainingBudget by remember(summary.monthlyBudget, summary.spent) {
        derivedStateOf {
            summary.monthlyBudget - summary.spent
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Budget Overview")
        }

        item {
            BudgetInfoCard(
                title = "Monthly Budget",
                value = "$${"%.2f".format(summary.monthlyBudget)}"
            )
        }

        item {
            BudgetInfoCard(
                title = "Spent",
                value = "$${"%.2f".format(summary.spent)}"
            )
        }

        item {
            BudgetInfoCard(
                title = "Remaining",
                value = "$${"%.2f".format(remainingBudget)}"
            )
        }
    }
}