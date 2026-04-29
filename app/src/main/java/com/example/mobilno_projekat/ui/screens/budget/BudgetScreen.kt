package com.example.mobilno_projekat.ui.screens.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
        BudgetUiState.Loading -> {
            Text("Loading budget...")
        }

        is BudgetUiState.Error -> {
            Text(state.message)
        }

        is BudgetUiState.Success -> {
            BudgetScreenStateful(
                summary = state.summary,
                onBack = onBack,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BudgetScreenStateful(
    summary: BudgetSummary,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spentPercentage by remember(summary) {
        derivedStateOf {
            if (summary.monthlyBudget == 0.0) 0
            else ((summary.spent / summary.monthlyBudget) * 100).toInt()
        }
    }

    val budgetStatus by remember(summary) {
        derivedStateOf {
            when {
                summary.remaining < 0 -> "Over budget"
                summary.remaining == 0.0 -> "Budget fully used"
                else -> "Within budget"
            }
        }
    }

    BudgetScreenContent(
        summary = summary,
        spentPercentage = spentPercentage,
        budgetStatus = budgetStatus,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun BudgetScreenContent(
    summary: BudgetSummary,
    spentPercentage: Int,
    budgetStatus: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Budget Overview",
            style = MaterialTheme.typography.headlineMedium
        )

        BudgetInfoCard(
            title = "Monthly Budget",
            value = "$${"%.2f".format(summary.monthlyBudget)}"
        )

        BudgetInfoCard(
            title = "Spent",
            value = "$${"%.2f".format(summary.spent)}"
        )

        BudgetInfoCard(
            title = "Remaining",
            value = "$${"%.2f".format(summary.remaining)}"
        )

        BudgetInfoCard(
            title = "Spent Percentage",
            value = "$spentPercentage%"
        )

        BudgetInfoCard(
            title = "Status",
            value = budgetStatus
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}