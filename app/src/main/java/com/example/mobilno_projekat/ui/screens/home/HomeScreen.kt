package com.example.mobilno_projekat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.home.components.DashboardCard

@Composable
fun HomeScreen(
    totalBalance: Double,
    onOpenScreen: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Personal Budget Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

        DashboardCard(
            title = "Current Balance",
            value = "$${"%.2f".format(totalBalance)}"
        )

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = { onOpenScreen(AppScreen.TRANSACTIONS) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Transactions")
        }

        Button(
            onClick = { onOpenScreen(AppScreen.ADD_TRANSACTION) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Transaction")
        }

        Button(
            onClick = { onOpenScreen(AppScreen.CATEGORIES) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Categories")
        }

        Button(
            onClick = { onOpenScreen(AppScreen.BUDGET) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Budget")
        }
    }
}