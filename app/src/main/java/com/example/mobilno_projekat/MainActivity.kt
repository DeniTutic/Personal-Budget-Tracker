package com.example.mobilno_projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobilno_projekat.navigation.NavGraph
import com.example.mobilno_projekat.navigation.bottomNavItems
import com.example.mobilno_projekat.navigation.getBottomNavRoutes
import com.example.mobilno_projekat.ui.theme.MobilnoProjekatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobilnoProjekatTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route
                val bottomNavRoutes = getBottomNavRoutes()
                val showBottomBar = currentRoute in bottomNavRoutes

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Personal Budget Tracker") },
                            navigationIcon = {
                                if (!showBottomBar) {
                                    TextButton(onClick = { navController.navigateUp() }) {
                                        Text("Back")
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar {
                                bottomNavItems.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = { Text(item.iconText) },
                                        label = { Text(item.label) }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}