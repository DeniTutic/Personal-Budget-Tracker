package com.example.mobilno_projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.mobilno_projekat.ui.screens.AppScreen
import com.example.mobilno_projekat.ui.screens.home.HomeScreen
import com.example.mobilno_projekat.ui.theme.MobilnoProjekatTheme
import com.example.mobilno_projekat.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobilnoProjekatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

                    when (currentScreen) {
                        AppScreen.HOME -> HomeScreen(
                            totalBalance = 620.0,
                            onOpenScreen = { selectedScreen ->
                                currentScreen = selectedScreen
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}