package com.example.mobilno_projekat.ui.screens.add

sealed interface AddTransactionNavigationEvent {
    data object NavigateBack : AddTransactionNavigationEvent
}