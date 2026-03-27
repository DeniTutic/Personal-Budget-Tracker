package com.example.mobilno_projekat.model

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String
)