package com.example.mobilno_projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobilno_projekat.navigation.NavGraph
import com.example.mobilno_projekat.navigation.Screen
import com.example.mobilno_projekat.navigation.bottomNavItems
import com.example.mobilno_projekat.navigation.getBottomNavRoutes
import com.example.mobilno_projekat.ui.theme.MobilnoProjekatTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.MaterialTheme

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
                val authRoutes = listOf(
                    Screen.SignUp.route,
                    Screen.Login.route
                )

                val showBottomBar = currentRoute in bottomNavRoutes
                val showTopBar = currentRoute !in authRoutes

                AnimatedContent(
                    targetState = currentRoute,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "ScreenFadeAnimation"
                ) { targetRoute ->
                    targetRoute

                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.background,
                        topBar = {
                            if (showTopBar) {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Personal Budget Tracker",
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    },
                                    navigationIcon = {
                                        if (!showBottomBar) {
                                            TextButton(onClick = { navController.navigateUp() }) {
                                                Text(
                                                    text = "Back",
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        },
                        bottomBar = {
                            if (showBottomBar) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ) {
                                    bottomNavItems.forEach { item ->
                                        NavigationBarItem(
                                            selected = currentRoute == item.route,
                                            onClick = {
                                                if (item.route == Screen.Home.route) {
                                                    navController.popBackStack(
                                                        route = Screen.Home.route,
                                                        inclusive = false
                                                    )
                                                } else {
                                                    navController.navigate(item.route) {
                                                        popUpTo(Screen.Home.route) {
                                                            saveState = true
                                                        }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            },
                                            icon = { Text(item.iconText) },
                                            label = { Text(item.label) },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                                indicatorColor = MaterialTheme.colorScheme.primary,
                                                unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                                unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
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
}