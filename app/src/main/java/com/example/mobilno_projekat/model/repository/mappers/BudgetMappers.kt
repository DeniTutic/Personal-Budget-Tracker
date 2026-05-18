package com.example.mobilno_projekat.model.repository.mappers

import com.example.mobilno_projekat.model.data.local.entity.CategoryEntity
import com.example.mobilno_projekat.model.data.local.entity.TransactionEntity
import com.example.mobilno_projekat.ui.screens.categories.model.Category
import com.example.mobilno_projekat.ui.screens.transactions.model.Transaction

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        id = id,
        title = title,
        amount = amount,
        type = type
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name
    )
}