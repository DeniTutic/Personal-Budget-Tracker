package com.example.mobilno_projekat.ui.screens.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.model.BudgetSummary
import com.example.mobilno_projekat.ui.screens.budget.components.BudgetInfoCard

@Composable
fun BudgetScreen(
    summary: BudgetSummary,
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

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}